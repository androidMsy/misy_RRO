package com.msy.rrodemo.presenter;

import com.msy.rrodemo.basic.RxPresenter;
import com.msy.rrodemo.entity.UserBean;
import com.msy.rrodemo.net.RetrofitHelper;
import com.msy.rrodemo.net.callback.BaseResponseCall;
import com.msy.rrodemo.net.scheduler.IoMainScheduler;
import com.msy.rrodemo.net.scheduler.ResponseTransformer;
import com.msy.rrodemo.view.MineView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2019/12/24/024.
 */

public class MinePresenter extends RxPresenter<MineView.View> implements MineView.Presenter<MineView.View> {

    RetrofitHelper helper;

    @Inject
    public MinePresenter(RetrofitHelper helper){
        this.helper = helper;
    }

    @Override
    public void getUser() {
        Disposable disposable = helper.getUser()
                .compose(new IoMainScheduler<>())
                .compose(ResponseTransformer.handleResult())
                .subscribe(new BaseResponseCall<UserBean>(mView) {
                    @Override
                    public void onAccept(UserBean userBean) {
                        myHandler.post(()-> mView.setData(userBean));
                    }

                }, new BaseResponseCall<Throwable>(mView) {
                    @Override
                    public void onAccept(Throwable throwable) {
                        mView.showError(throwable.getMessage());
                    }
                });
        addSubscribe(disposable);
    }

    @Override
    public void loginout() {
        Disposable disposable = helper.loginout()
                .compose(new IoMainScheduler<>())
                .compose(ResponseTransformer.handleResult())
                .subscribe(new BaseResponseCall<String>(mView) {
                    @Override
                    public void onAccept(String s) {
                        myHandler.post(() -> mView.loginoutSuccess());
                    }
                }, new BaseResponseCall<Throwable>(mView) {
                    @Override
                    public void onAccept(Throwable throwable) {
                        mView.showError(throwable.getMessage());
                    }
                });
        addSubscribe(disposable);
    }
}
