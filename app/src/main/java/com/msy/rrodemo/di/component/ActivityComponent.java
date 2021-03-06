package com.msy.rrodemo.di.component;

import android.app.Activity;

import com.msy.rrodemo.di.module.ActivityModule;
import com.msy.rrodemo.di.scope.ActivityScope;
import com.msy.rrodemo.ui.MainActivity;
import com.msy.rrodemo.ui.StatusActivity;

import dagger.Component;

/**
 * Created by Administrator on 2019/9/27/027.
 */

@ActivityScope
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(MainActivity mainActivity);

    void inject(StatusActivity statusActivity);
}
