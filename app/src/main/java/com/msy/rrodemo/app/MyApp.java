package com.msy.rrodemo.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.msy.rrodemo.di.component.AppComponent;
import com.msy.rrodemo.di.component.DaggerAppComponent;
import com.msy.rrodemo.di.module.AppModule;
import com.msy.rrodemo.di.module.HttpModule;
import com.msy.rrodemo.utils.SPUtils;

import static com.msy.rrodemo.contacts.AboutSPContacts.LOGIN_STATUS;
import static com.msy.rrodemo.contacts.AboutSPContacts.USER_SP_KEY;

/**
 * Created by Administrator on 2019/9/27/027.
 */

public class MyApp extends MultiDexApplication {

    public AppComponent mAppComponent;
    private static MyApp mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initAppComponent();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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

    public static boolean isLogin(){
        return SPUtils.getInstance(USER_SP_KEY).getBoolean(LOGIN_STATUS, false);
    }
    public static Context getAppContext(){
        return getApplication().getApplicationContext();
    }
}
