package com.msy.rrodemo.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.msy.rrodemo.basic.BaseActivity;
import com.msy.rrodemo.R;
import com.msy.rrodemo.entity.MainBean;
import com.msy.rrodemo.entity.UserBean;
import com.msy.rrodemo.presenter.MainPresenter;
import com.msy.rrodemo.view.MainView;


/**
 * Created by Administrator on 2019/9/26/026.
 */

public class MainActivity extends BaseActivity<MainPresenter> implements View.OnClickListener,MainView.View {

    private Button requestBtn;
    private TextView contentTv;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget(){
        requestBtn = findViewById(R.id.request_btn);
        contentTv = findViewById(R.id.content_tv);
        findViewById(R.id.menu_img).setOnClickListener(this);
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void initToolbar(){
    }

    @Override
    public void initListner(){
        requestBtn.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        mPresenter.getData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.menu_img:
                startActivity(new Intent(this, StatusActivity.class));
                break;
            default:

                break;
        }
    }

    @Override
    public void setData(MainBean bean) {
        String str = "";
        for (UserBean user : bean.getList()){
            str += user.getIntro() + "----";
        }
        contentTv.setText(str);
    }

}
