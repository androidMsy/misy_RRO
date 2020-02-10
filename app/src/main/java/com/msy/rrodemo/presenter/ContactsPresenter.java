package com.msy.rrodemo.presenter;

import android.os.Handler;
import android.os.Looper;

import com.msy.rrodemo.basic.RxPresenter;
import com.msy.rrodemo.entity.BaseCollection;
import com.msy.rrodemo.entity.UserBean;
import com.msy.rrodemo.net.RetrofitHelper;
import com.msy.rrodemo.net.callback.BaseResponseCall;
import com.msy.rrodemo.net.scheduler.IoMainScheduler;
import com.msy.rrodemo.net.scheduler.ResponseTransformer;
import com.msy.rrodemo.view.ContactsView;


import javax.inject.Inject;

/**
 * Created by Administrator on 2019/12/23/023.
 */

public class ContactsPresenter extends RxPresenter<ContactsView.View> implements ContactsView.Presenter<ContactsView.View> {

    RetrofitHelper helper;

    @Inject
    public ContactsPresenter(RetrofitHelper helper){
        this.helper = helper;
    }

    @Override
    public void getAllContacts(int mPage) {
        helper.getAllContacts(mPage)
                .compose(new IoMainScheduler<>())
                .compose(ResponseTransformer.handleResult())
                .subscribe(new BaseResponseCall<BaseCollection<UserBean>>(mView) {
                    @Override
                    public void onAccept(BaseCollection<UserBean> userBeanBaseCollection) {
                        mView.setData(userBeanBaseCollection);
                    }
                }, new BaseResponseCall<Throwable>(mView) {
                    @Override
                    public void onAccept(Throwable throwable) {
                        mView.showError(throwable.getMessage());
                    }
                });
    }
}
