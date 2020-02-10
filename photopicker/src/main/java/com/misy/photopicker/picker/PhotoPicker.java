package com.misy.photopicker.picker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

import com.misy.photopicker.PhotoPickerActivity;
import com.misy.photopicker.utils.PermissionsUtils;

/**
 * Created by Administrator on 2020/1/6/006.
 */

public class PhotoPicker {

    public static final int MAX_SELECT_COUNT = 9;

    public static final String EXTRA_MAX_COUNT = "MAX_COUNT";
    public static final String EXTRA_SHOW_CAMERA = "SHOW_CAMERA";


    public static final String PHOTO_PICKER_KEY = "photo_picker_key";
    public static final int REQUEST_CODE = 10001;
    public static final int RESULT_CODE = 211;

    public static PhotoPickerBuilder build(){
        return new PhotoPickerBuilder();
    }

    public static class PhotoPickerBuilder{

        private Intent mIntent;
        private Bundle optionBundle;

        private PhotoPickerBuilder(){
            mIntent = new Intent();
            optionBundle = new Bundle();
        }
        public PhotoPickerBuilder maxSelectNum(int maxCount){
            optionBundle.putInt(EXTRA_MAX_COUNT, maxCount > MAX_SELECT_COUNT ? MAX_SELECT_COUNT : maxCount);
            return this;
        }
        public PhotoPickerBuilder showCamera(boolean isShow){
            optionBundle.putBoolean(EXTRA_SHOW_CAMERA, isShow);
            return this;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        public void start(@Nullable Activity mContext, int code){
            if (PermissionsUtils.checkReadStoragePermission(mContext)){
                mContext.startActivityForResult(getIntent(mContext), code);
            }
            PermissionsUtils.checkWriteStoragePermission(mContext);
        }

        public void start(@Nullable Activity mContext){
            if (PermissionsUtils.checkReadStoragePermission(mContext)){
                mContext.startActivityForResult(getIntent(mContext), REQUEST_CODE);
            }
        }

        private Intent getIntent(Context mContext){
            mIntent.setClass(mContext, PhotoPickerActivity.class);
            mIntent.putExtras(optionBundle);
            return mIntent;
        }
    }


}
