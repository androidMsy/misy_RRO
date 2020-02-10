package com.msy.rrodemo.basic;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.msy.rrodemo.R;
import com.msy.rrodemo.app.MyApp;
import com.msy.rrodemo.di.component.ActivityComponent;
import com.msy.rrodemo.di.component.DaggerActivityComponent;
import com.msy.rrodemo.di.module.ActivityModule;
import com.msy.rrodemo.utils.SPUtils;
import com.msy.rrodemo.utils.StatusBarUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;

import static com.msy.rrodemo.contacts.AboutSPContacts.HEADER_URL;
import static com.msy.rrodemo.contacts.AboutSPContacts.REALNAME;
import static com.msy.rrodemo.contacts.AboutSPContacts.USER_ID;
import static com.msy.rrodemo.contacts.AboutSPContacts.USER_SP_KEY;

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
        ButterKnife.bind(this);
        mToolbar = this.findViewById(R.id.toolbar);
        initStatusBar();
        initToolbar();
        initInject();
        initPresenter();
        initWidget();
        initListner();
        initIntentData();
        initData();
        bindService();
        initRecyclerView();
        loadData();
    }

    public ActivityComponent getActivityComponent(){
        return DaggerActivityComponent.builder()
                .appComponent(MyApp.getApplication().getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    private void initStatusBar(){
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.base_color));
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

    protected void initData(){

    }

    protected void bindService(){

    }

    protected void initRecyclerView(){

    }

    protected void loadData(){

    }

    protected String getUserId(){
        return SPUtils.getInstance(USER_SP_KEY).getString(USER_ID);
    }

    protected String getHeaderUrl(){
        return SPUtils.getInstance(USER_SP_KEY).getString(HEADER_URL);
    }

    protected String getRealName(){
        return SPUtils.getInstance(USER_SP_KEY).getString(REALNAME);
    }

    protected void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

        View view = getCurrentFocus();
        if (view != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
