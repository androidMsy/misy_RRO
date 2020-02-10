package com.msy.rrodemo.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.msy.rrodemo.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2019/12/19/019.
 */

@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment){
        this.mFragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity(){
        return mFragment.getActivity();
    }
}
