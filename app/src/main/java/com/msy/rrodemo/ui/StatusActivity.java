package com.msy.rrodemo.ui;

import android.view.View;
import android.widget.Toast;

import com.msy.rrodemo.R;
import com.msy.rrodemo.basic.BaseActivity;
import com.msy.rrodemo.entity.StatusBean;
import com.msy.rrodemo.presenter.StatusPresenter;
import com.msy.rrodemo.view.StatusView;
import com.msy.rrodemo.widget.MultipleStatusView;

/**
 * Created by Administrator on 2019/10/16/016.
 */

public class StatusActivity extends BaseActivity<StatusPresenter> implements StatusView.View{

    private MultipleStatusView mMultipleStatusView;
    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_status;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initWidget() {
        mMultipleStatusView = findViewById(R.id.mMultipleStatusView);
        mMultipleStatusView.showLoading();
        mMultipleStatusView.postDelayed(() -> mMultipleStatusView.showContent(), 2000);
        mMultipleStatusView.setOnRetryClickListener(v -> mMultipleStatusView.showContent());
    }

    @Override
    protected void initToolbar() {
        mToolbar.setTitle("Status");
        mToolbar.setNavigationIcon(R.mipmap.back_icon);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void initIntentData() {
        url = getIntent().getStringExtra("url");
    }

    @Override
    protected void loadData() {
        mPresenter.getData(url);
    }

    public void empty(View v){
        mMultipleStatusView.showEmpty();
    }
    public void error(View v){
        mMultipleStatusView.showError();
    }
    public void network(View v){
        mMultipleStatusView.showNoNetwork();
    }

    @Override
    public void setData(String bean) {
        Toast.makeText(this, bean, Toast.LENGTH_LONG).show();
    }
}
