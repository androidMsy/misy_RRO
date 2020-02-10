package com.msy.rrodemo.basic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.msy.rrodemo.app.MyApp;
import com.msy.rrodemo.di.component.DaggerFragmentComponent;
import com.msy.rrodemo.di.component.FragmentComponent;
import com.msy.rrodemo.di.module.FragmentModule;
import com.msy.rrodemo.ui.LoginActivity;
import com.msy.rrodemo.utils.SPUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.msy.rrodemo.contacts.AboutSPContacts.USER_ID;
import static com.msy.rrodemo.contacts.AboutSPContacts.USER_SP_KEY;

/**
 * Created by Administrator on 2019/12/19/019.
 */

public abstract class BaseFragment<T extends RxPresenter> extends Fragment implements BaseView{

    @Inject
    protected T mPresenter;
    protected View rootView;
    protected LayoutInflater inflate;
    protected Activity mActivity;
    protected Context mContext;
    protected Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != rootView){
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent){
                parent.removeView(rootView);
            }
        }else {
            rootView = inflater.inflate(getLayoutId(), container, false);
            mActivity = super.getActivity();
            mContext = mActivity;
            this.inflate = inflater;
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initInject();
        initPresenter();
        initVariables();
        initWidget();
        initToolbar();
        initDatas();
        initRecyclerView();
        loadData();
    }

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(MyApp.getApplication().getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    public abstract
    @LayoutRes int getLayoutId();

    protected void initInject(){

    }
    private void initPresenter(){
        if (mPresenter != null) mPresenter.attachView(this);
    }

    protected void initVariables(){

    }

    protected void initWidget(){

    }
    protected void initToolbar(){

    }
    protected void initDatas(){

    }

    protected void initRecyclerView(){

    }

    protected void loadData(){

    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void complete() {

    }

    protected String getUserId(){
        return SPUtils.getInstance(USER_SP_KEY).getString(USER_ID);
    }
    protected void judgeLoginStatus(){
        if (!MyApp.isLogin()){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            return;
        }
    }
    @Override
    public void onDestroy() {
        if (null != mPresenter)mPresenter.detachView();
        unbinder.unbind();
        super.onDestroy();
    }
}
