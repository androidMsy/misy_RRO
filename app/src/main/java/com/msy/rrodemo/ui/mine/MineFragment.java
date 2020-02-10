package com.msy.rrodemo.ui.mine;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.msy.rrodemo.R;
import com.msy.rrodemo.app.MyApp;
import com.msy.rrodemo.basic.BaseFragment;
import com.msy.rrodemo.entity.UserBean;
import com.msy.rrodemo.presenter.MinePresenter;
import com.msy.rrodemo.ui.LoginActivity;
import com.msy.rrodemo.ui.RegisterActivity;
import com.msy.rrodemo.utils.GlideUtils;
import com.msy.rrodemo.utils.SPUtils;
import com.msy.rrodemo.view.MineView;

import butterknife.BindView;
import butterknife.OnClick;

import static com.msy.rrodemo.contacts.AboutSPContacts.USER_SP_KEY;
import static com.msy.rrodemo.ui.LoginActivity.RESULT_CODE;

/**
 * Created by Administrator on 2019/12/19/019.
 */

public class MineFragment extends BaseFragment<MinePresenter> implements MineView.View,View.OnClickListener{

    @BindView(R.id.unlogin_layout)
    ViewGroup unloginLayout;
    @BindView(R.id.logined_layout)
    ViewGroup loginedLayout;
    @BindView(R.id.head_img)
    ImageView headImg;
    @BindView(R.id.username_tv)
    TextView usernameTv;
    @BindView(R.id.realname_tv)
    TextView realnameTv;
    @BindView(R.id.status_tv)
    TextView statusTv;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void loadData() {
        mPresenter.getUser();
    }

    @OnClick({R.id.register_btn,R.id.login_btn,R.id.loginout_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn:
                startActivity(new Intent(getActivity(), RegisterActivity.class));
                break;
            case R.id.login_btn:
                startActivityForResult(new Intent(getActivity(), LoginActivity.class), RESULT_CODE);
                break;
            case R.id.loginout_btn:
                mPresenter.loginout();
                break;
        }
    }

    private void switchLoginStatus(){
        if (MyApp.isLogin()){
            unloginLayout.setVisibility(View.GONE);
            loginedLayout.setVisibility(View.VISIBLE);
        }else {
            unloginLayout.setVisibility(View.VISIBLE);
            loginedLayout.setVisibility(View.GONE);
        }
        statusTv.setText(MyApp.isLogin() ? "已登录" : "未登录");
    }

    @Override
    public void setData(UserBean bean) {
        switchLoginStatus();
        GlideUtils.loadHeaderImg(getActivity(), bean.getHeaderUrl(), headImg);
        usernameTv.setText(bean.getUsername());
        realnameTv.setText(bean.getRealname());
    }

    @Override
    public void complete() {
        super.complete();
        switchLoginStatus();
    }

    @Override
    public void loginoutSuccess() {
        SPUtils.getInstance(USER_SP_KEY).clear();
        switchLoginStatus();
    }

    @Override
    public void onResume() {
        super.onResume();
        switchLoginStatus();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data){
            if (resultCode == RESULT_CODE){
                loadData();
                statusTv.setText("已登录");
            }
        }
    }
}
