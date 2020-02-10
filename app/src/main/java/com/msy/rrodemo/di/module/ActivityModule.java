package com.msy.rrodemo.di.module;

import android.app.Activity;

import com.msy.rrodemo.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2019/9/27/027.
 */

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}



