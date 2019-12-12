package com.msy.rrodemo.presenter;

import com.msy.rrodemo.net.RetrofitHelper;
import com.msy.rrodemo.basic.RxPresenter;
import com.msy.rrodemo.view.StatusView;

import javax.inject.Inject;

/**
 * Created by Administrator on 2019/10/17/017.
 */

public class StatusPresenter extends RxPresenter<StatusView.View> implements StatusView.Presenter<StatusView.View> {

    RetrofitHelper helper;

    @Inject
    public StatusPresenter(RetrofitHelper helper){
        this.helper = helper;
    }

    @Override
    public void getData(String url) {
        mView.setData(url);
    }
}
