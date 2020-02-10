package com.msy.rrodemo.presenter;

import com.msy.rrodemo.net.callback.BaseResponseCall;
import com.msy.rrodemo.net.RetrofitHelper;
import com.msy.rrodemo.basic.RxPresenter;
import com.msy.rrodemo.entity.MainBean;
import com.msy.rrodemo.net.scheduler.ResponseTransformer;
import com.msy.rrodemo.net.scheduler.IoMainScheduler;
import com.msy.rrodemo.view.HomeView;

import javax.inject.Inject;


/**
 * Created by Administrator on 2019/9/26/026.
 */

public class HomePresenter extends RxPresenter<HomeView.View> implements HomeView.Presenter<HomeView.View> {

    private RetrofitHelper helper;

    @Inject
    public HomePresenter(RetrofitHelper helper){
        this.helper = helper;
    }


    @Override
    public void getData() {
//        helper.getData()
//                .compose(new IoMainScheduler<>())
//                .compose(ResponseTransformer.handleResult())
//                .subscribe(new BaseResponseCall<MainBean>(mView) {
//                    @Override
//                    public void onAccept(MainBean mainBean) {
//                        mView.setData(mainBean);
//                    }
//                }, new BaseResponseCall<Throwable>(mView) {
//                    @Override
//                    public void onAccept(Throwable throwable) {
//                        mView.showError(throwable.getMessage());
//                    }
//                });
    }
}
