package com.misy.photopicker;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.misy.photopicker.adapter.PhotoAdapter;
import com.misy.photopicker.listener.OnCameraListener;
import com.misy.photopicker.listener.OnItemCheckChangeListener;
import com.misy.photopicker.picker.PhotoPicker;
import com.misy.photopicker.utils.PermissionsUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.misy.photopicker.picker.PhotoPicker.PHOTO_PICKER_KEY;
import static com.misy.photopicker.picker.PhotoPicker.RESULT_CODE;

/**
 * Created by Administrator on 2020/1/6/006.
 */

public class PhotoPickerActivity extends AppCompatActivity implements View.OnClickListener,OnItemCheckChangeListener,OnCameraListener {

    private RecyclerView photoView;
    private Button enterBtn;
    private ImageView backImg;

    private PhotoAdapter adapter;

    public int maxSelectCounts;
    public boolean isShowCamera;
    private String[] fileType = new String[]{"image/jpeg","image/png"};
    private List<String> pathList = new ArrayList<>();
    private File cameraPhotoFile;

    private static final int CAMERA_REQUEST_CODE = 3210;

    private Handler myHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            Collections.reverse(pathList);
            GridLayoutManager manager = new GridLayoutManager(PhotoPickerActivity.this, 3);
            photoView.setLayoutManager(manager);
            adapter = new PhotoAdapter(PhotoPickerActivity.this, pathList);
            photoView.setAdapter(adapter);
            adapter.setOnItemCheckChangeListener(PhotoPickerActivity.this);
            adapter.setOnCameraListener(PhotoPickerActivity.this);
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_picker);
        initVariable();
        initWidget();
        initToolbar();
        initListener();
        getAllPhoto();
    }

    private void initVariable(){
        Bundle optionBundle = getIntent().getExtras();
        maxSelectCounts = optionBundle.getInt(PhotoPicker.EXTRA_MAX_COUNT);
        isShowCamera = optionBundle.getBoolean(PhotoPicker.EXTRA_SHOW_CAMERA);
    }

    private void initWidget(){
        photoView = findViewById(R.id.photo_view);
        enterBtn = findViewById(R.id.enter_btn);
        backImg = findViewById(R.id.back_img);
    }

    private void initToolbar(){

    }

    private void initListener(){
        enterBtn.setOnClickListener(this);
        backImg.setOnClickListener(this);
    }

    private void getAllPhoto(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = contentResolver.query(imageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?", fileType, MediaStore.Images.Media.DATE_MODIFIED);
                if(null == cursor){
                    return;
                }
                while (cursor.moveToNext()){
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    pathList.add(path);
                }
                if (null != cursor & !cursor.isClosed()){
                    cursor.close();
                }
                myHandler.sendEmptyMessage(0);
            }
        }).start();
    }

    @Override
    public void onItemCheckChanged(Map<Integer, Boolean> checkStatusMap, int checkNum) {
        enterBtn.setText("完成(" + checkNum + "/" + maxSelectCounts + ")");
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.enter_btn){
            List checkPathList = adapter.getCheckedPathList();
            Intent pickerIntent = new Intent();
            pickerIntent.putExtra(PHOTO_PICKER_KEY, (Serializable) checkPathList);
            setResult(RESULT_CODE, pickerIntent);
            finish();
        }else if (view.getId() == R.id.back_img){
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCamera() {
        if (PermissionsUtils.checkCameraPermission(this) && PermissionsUtils.checkWriteStoragePermission(this)){
            cameraPhotoFile = new File(getExternalCacheDir(), System.currentTimeMillis() + "forpicker.png");
            try {
                cameraPhotoFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Uri photoUri;
            if(Build.VERSION.SDK_INT>=24){
                photoUri = FileProvider.getUriForFile(this,"com.misy.photopicker.utils.PhotoFileProvider",cameraPhotoFile);
            }else {
                photoUri = Uri.fromFile(cameraPhotoFile);
            }
            Intent cameraIntent = new Intent();
            cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            List<String> list = new ArrayList<>();
            list.add(cameraPhotoFile.getAbsolutePath());
            Intent pickerIntent = new Intent();
            pickerIntent.putExtra(PHOTO_PICKER_KEY, (Serializable) list);
            setResult(RESULT_CODE, pickerIntent);
            finish();
        }else {
            if (cameraPhotoFile.exists())cameraPhotoFile.delete();
        }
    }
}
