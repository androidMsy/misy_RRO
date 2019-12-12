package com.msy.rrodemo.di.component;

import android.content.Context;

import com.msy.rrodemo.net.RetrofitHelper;
import com.msy.rrodemo.di.module.AppModule;
import com.msy.rrodemo.di.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2019/9/27/027.
 */

@Singleton
@Component(modules = {AppModule.class, HttpModule.class} )
public interface AppComponent {
    Context getContext();

    RetrofitHelper getRetrofitHelper();
}
