package com.msy.rrodemo.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.msy.rrodemo.R;
import com.msy.rrodemo.basic.BaseActivity;
import com.msy.rrodemo.entity.UserBean;
import com.msy.rrodemo.presenter.RegisterPresenter;
import com.msy.rrodemo.utils.SPUtils;
import com.msy.rrodemo.utils.Toast;
import com.msy.rrodemo.view.RegisterView;

import butterknife.BindView;
import butterknife.OnClick;

import static com.msy.rrodemo.contacts.AboutSPContacts.*;

/**
 * Created by Administrator on 2019/12/20/020.
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterView.View,View.OnClickListener{

    @BindView(R.id.username_edt)
    EditText unameEdt;
    @BindView(R.id.password_edt)
    EditText pswdEdt;

    private String uname;
    private String pswd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void registerSuccess(UserBean userBean) {
        SPUtils.getInstance(COMMONT_SP_KEY).put(USERNAME, userBean.getUsername());
        Toast.show(this, "注册成功");
        finish();
    }

    @OnClick({R.id.register_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn:
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
                mPresenter.register(uname, pswd);
                break;
        }
    }
}
