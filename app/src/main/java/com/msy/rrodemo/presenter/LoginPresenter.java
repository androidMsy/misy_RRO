package com.msy.rrodemo.presenter;

import android.util.Log;

import com.msy.rrodemo.basic.RxPresenter;
import com.msy.rrodemo.entity.UserBean;
import com.msy.rrodemo.net.RetrofitHelper;
import com.msy.rrodemo.net.callback.BaseResponseCall;
import com.msy.rrodemo.net.scheduler.IoMainScheduler;
import com.msy.rrodemo.net.scheduler.ResponseTransformer;
import com.msy.rrodemo.view.LoginView;

import javax.inject.Inject;

/**
 * Created by Administrator on 2019/12/19/019.
 */

public class LoginPresenter extends RxPresenter<LoginView.View> implements LoginView.Presenter<LoginView.View> {

    private static final String TAG = "LoginPresenter";

    RetrofitHelper helper;

    @Inject
    public LoginPresenter(RetrofitHelper helper){
        this.helper = helper;
    }

    @Override
    public void login(String username, String pswd) {
        helper.login(username, pswd)
                .compose(new IoMainScheduler<>())
                .compose(ResponseTransformer.handleResult())
                .subscribe(new BaseResponseCall<UserBean>(mView) {
                    @Override
                    public void onAccept(UserBean userBean) {
                        mView.loginSuccess(userBean);
                    }
                }, new BaseResponseCall<Throwable>(mView) {
                    @Override
                    public void onAccept(Throwable throwable) {
                        mView.showError(throwable.getMessage());
                        Log.d(TAG, throwable.getMessage());
                    }
                });
    }
}
