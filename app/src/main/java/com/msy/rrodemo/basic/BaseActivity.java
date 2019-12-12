package com.msy.rrodemo.basic;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


import com.msy.rrodemo.R;
import com.msy.rrodemo.app.MyApp;
import com.msy.rrodemo.di.component.ActivityComponent;
import com.msy.rrodemo.di.component.DaggerActivityComponent;
import com.msy.rrodemo.di.module.ActivityModule;
import com.msy.rrodemo.utils.StatusBarUtil;

import javax.inject.Inject;

/**
 * Created by Administrator on 2019/9/26/026.
 */

public abstract class BaseActivity<T extends RxPresenter> extends AppCompatActivity implements BaseView {

    @Inject
    protected T mPresenter;

    public Toolbar mToolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mToolbar = this.findViewById(R.id.toolbar);
        initStatusBar();
        initToolbar();
        initInject();
        initPresenter();
        initWidget();
        initListner();
        initIntentData();
        loadData();
    }

    public ActivityComponent getActivityComponent(){
        return DaggerActivityComponent.builder()
                .appComponent(MyApp.getApplication().getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    private void initStatusBar(){
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.colorPrimary));
    }

    private void initPresenter(){
        if (null != mPresenter){
            mPresenter.attachView(this);
        }
    }

    @Override
    public void showError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void complete() {

    }

    @Override
    protected void onDestroy() {
        if (null != mPresenter)
            mPresenter.detachView();
        super.onDestroy();
    }

    protected abstract @LayoutRes int getLayoutId();

    protected void initToolbar(){

    }

    protected void initWidget(){

    }

    protected void initListner(){

    }

    protected void initInject(){

    }

    protected void initIntentData(){

    }

    protected void loadData(){

    }
}
