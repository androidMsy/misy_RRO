package com.msy.rrodemo.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.msy.rrodemo.R;
import com.msy.rrodemo.basic.BaseActivity;
import com.msy.rrodemo.entity.UserBean;
import com.msy.rrodemo.presenter.LoginPresenter;
import com.msy.rrodemo.utils.SPUtils;
import com.msy.rrodemo.utils.Toast;
import com.msy.rrodemo.view.LoginView;

import butterknife.BindView;
import butterknife.OnClick;

import static com.msy.rrodemo.contacts.AboutSPContacts.*;

/**
 * Created by Administrator on 2019/12/19/019.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements View.OnClickListener,LoginView.View{

    @BindView(R.id.username_edt)
    EditText unameEdt;
    @BindView(R.id.password_edt)
    EditText pswdEdt;

    private String uname;
    private String pswd;
    public static final int RESULT_CODE = 2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.login_btn,R.id.register_btn})
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.login_btn:
                uname = unameEdt.getText().toString().trim();
                pswd = pswdEdt.getText().toString().trim();
                if (TextUtils.isEmpty(uname)){
                    Toast.show(this, "请输入用户名");
                    return;
                }
                if (TextUtils.isEmpty(pswd)){
                    Toast.show(this, "请输入密码");
                    return;
                }
                mPresenter.login(uname, pswd);
                break;
            case R.id.register_btn:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    @Override
    public void loginSuccess(UserBean userBean) {
        SPUtils.getInstance(COMMONT_SP_KEY).put(USERNAME, userBean.getUsername());
        SPUtils.getInstance(USER_SP_KEY).put(USER_ID, userBean.getUserId());
        SPUtils.getInstance(USER_SP_KEY).put(REALNAME, userBean.getRealname());
        SPUtils.getInstance(USER_SP_KEY).put(HEADER_URL, userBean.getHeaderUrl());
        SPUtils.getInstance(USER_SP_KEY).put(AUTH_TOKEN, userBean.getToken());
        SPUtils.getInstance(USER_SP_KEY).put(LOGIN_STATUS, true);
        setResult(RESULT_CODE, new Intent());
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        unameEdt.setText(SPUtils.getInstance(COMMONT_SP_KEY).getString(USERNAME));
    }
}
