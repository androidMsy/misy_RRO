package com.msy.rrodemo.presenter;

import com.msy.rrodemo.basic.RxPresenter;
import com.msy.rrodemo.entity.UserBean;
import com.msy.rrodemo.net.RetrofitHelper;
import com.msy.rrodemo.net.callback.BaseResponseCall;
import com.msy.rrodemo.net.scheduler.IoMainScheduler;
import com.msy.rrodemo.net.scheduler.ResponseTransformer;
import com.msy.rrodemo.view.RegisterView;

import javax.inject.Inject;

/**
 * Created by Administrator on 2019/12/20/020.
 */

public class RegisterPresenter extends RxPresenter<RegisterView.View> implements RegisterView.Presenter<RegisterView.View>  {

    private RetrofitHelper helper;

    @Inject
    public RegisterPresenter(RetrofitHelper helper){
        this.helper = helper;
    }

    @Override
    public void register(String uname, String pswd) {
        helper.register(uname, pswd)
                .compose(new IoMainScheduler<>())
                .compose(ResponseTransformer.handleResult())
                .subscribe(new BaseResponseCall<UserBean>(mView) {
                    @Override
                    public void onAccept(UserBean userBean) {
                        mView.registerSuccess(userBean);
                    }
                }, new BaseResponseCall<Throwable>(mView) {
                    @Override
                    public void onAccept(Throwable throwable) {

                    }
                });
    }
}
