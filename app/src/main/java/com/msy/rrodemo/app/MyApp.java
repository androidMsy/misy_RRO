package com.msy.rrodemo.app;

import android.app.Application;

import com.msy.rrodemo.di.component.AppComponent;
import com.msy.rrodemo.di.component.DaggerAppComponent;
import com.msy.rrodemo.di.module.AppModule;
import com.msy.rrodemo.di.module.HttpModule;

/**
 * Created by Administrator on 2019/9/27/027.
 */

public class MyApp extends Application {

    public AppComponent mAppComponent;
    private static MyApp mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initAppComponent();
    }

    private void initAppComponent(){
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .httpModule(new HttpModule())
                .build();
    }

    public static MyApp getApplication(){
        return mApp;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
