package com.msy.rrodemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.msy.rrodemo.R;
import com.msy.rrodemo.utils.StatusBarUtil;

/**
 * Created by Administrator on 2019/10/16/016.
 */

public class SplashActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucent(this);
        setContentView(R.layout.activity_splash);
        ImageView showImg = findViewById(R.id.show_img);
        showImg.postDelayed(() -> {startActivity(new Intent(this, MainActivity.class));
        finish();}, 3000);
    }
}
