package com.msy.rrodemo.utils;

import android.content.Context;

/**
 * Created by Administrator on 2019/12/19/019.
 */

public class Toast {
    public static void show(Context context, String content){
        android.widget.Toast.makeText(context, content, android.widget.Toast.LENGTH_SHORT).show();
    }
}
