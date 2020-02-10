package com.msy.rrodemo.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by Administrator on 2019/12/23/023.
 */

public class GlideUtils {

    public static void loadHeaderImg(Context mContext, String url, ImageView imageView){
        Glide.with(mContext)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);

    }
    public static void loadImg(Context mContext, String url, ImageView imageView){
        Glide.with(mContext)
                .load(url)
                .into(imageView);
    }

}
