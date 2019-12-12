package com.msy.rrodemo.di.module;

import android.content.Context;

import com.msy.rrodemo.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2019/9/27/027.
 */

@Module
public class AppModule {
    private Context mContext;
    public AppModule(Context mContext){
        this.mContext = mContext;
    }

    @Provides
    public Context provideAppModule(){
        return mContext;
    }
}
