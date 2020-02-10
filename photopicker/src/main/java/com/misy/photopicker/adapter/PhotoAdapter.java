package com.misy.photopicker.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.misy.photopicker.PhotoPickerActivity;
import com.misy.photopicker.R;
import com.misy.photopicker.listener.OnCameraListener;
import com.misy.photopicker.listener.OnItemCheckChangeListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2020/1/9/009.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {


    private PhotoPickerActivity mContext;
    private List<String> mList;
    private Map<Integer, Boolean> checkStatusMap = new HashMap<>();
    private List<String> checkedPathList = new ArrayList<>();
    private int checkNum = 0;
    private OnItemCheckChangeListener listener;
    private OnCameraListener cameraListener;

    private static final int CAMERA_ITEM_VIEW = 0;
    private static final int PHOTO_ITEM_VIEW = 1;

    public List<String> getCheckedPathList() {
        return checkedPathList;
    }

    public PhotoAdapter(PhotoPickerActivity mContext, List<String> pathList){
        this.mContext = mContext;
        this.mList = pathList;
        for (int i = 0; i < mList.size(); i++) {
            checkStatusMap.put(i, false);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_pic, null);
        ViewHolder holder = new ViewHolder(rootView);
        holder.picImg = rootView.findViewById(R.id.pic_img);
        holder.picCheck = rootView.findViewById(R.id.pic_check);
        holder.checkStatusView = rootView.findViewById(R.id.check_status_view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int index) {
        if (getItemViewType(index) == CAMERA_ITEM_VIEW){
            holder.picCheck.setVisibility(View.GONE);
            holder.picImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.photo_picker_camera));
            holder.checkStatusView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cameraListener.onCamera();
                }
            });
        }else {
            final int position = mContext.isShowCamera ? index - 1 : index;
            holder.picCheck.setChecked(checkStatusMap.get(position));
            Glide.with(mContext).load(mList.get(position)).into(holder.picImg);
            holder.picCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!checkStatusMap.get(position) && checkNum == mContext.maxSelectCounts){
                        holder.picCheck.setChecked(false);
                        Toast.makeText(mContext, "你最多只能选择" + checkNum + "张图片", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (checkStatusMap.get(position)){
                        checkedPathList.remove(mList.get(position));
                        holder.checkStatusView.setBackgroundColor(Color.parseColor("#22000000"));
                    }else {
                        checkedPathList.add(mList.get(position));
                        holder.checkStatusView.setBackgroundColor(Color.parseColor("#8a000000"));
                    }
                    checkStatusMap.put(position, !checkStatusMap.get(position));
                    checkNum = checkStatusMap.get(position) ? checkNum+1 : checkNum-1;
                    listener.onItemCheckChanged(checkStatusMap, checkNum);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mContext.isShowCamera){
            if (position == CAMERA_ITEM_VIEW){
                return CAMERA_ITEM_VIEW;
            }else {
                return PHOTO_ITEM_VIEW;
            }
        }else {
            return PHOTO_ITEM_VIEW;
        }

    }

    @Override
    public int getItemCount() {
        return mContext.isShowCamera ? mList.size() + 1 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView picImg;
        CheckBox picCheck;
        View checkStatusView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void setOnItemCheckChangeListener(OnItemCheckChangeListener listener){
        this.listener = listener;
    }

    public void setOnCameraListener(OnCameraListener cameraListener){
        this.cameraListener = cameraListener;
    }
}
