package com.msy.rrodemo.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.msy.rrodemo.app.MyApp;
import com.msy.rrodemo.basic.BasePresenter;
import com.msy.rrodemo.basic.RxPresenter;
import com.msy.rrodemo.entity.BaseCollection;
import com.msy.rrodemo.entity.ChatBean;
import com.msy.rrodemo.net.RetrofitHelper;
import com.msy.rrodemo.net.callback.BaseResponseCall;
import com.msy.rrodemo.net.scheduler.IoMainScheduler;
import com.msy.rrodemo.net.scheduler.ResponseTransformer;
import com.msy.rrodemo.service.MyWebSocketClientService;
import com.msy.rrodemo.ui.ChatActivity;
import com.msy.rrodemo.view.ChatView;
import com.msy.rrodemo.websocket.MyWebSocketClient;

import java.io.File;
import java.net.URI;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * Created by Administrator on 2019/12/16/016.
 */

public class ChatPresenter extends RxPresenter<ChatView.View> implements ChatView.Presenter<ChatView.View> {

    private RetrofitHelper helper;

    @Inject
    public ChatPresenter(RetrofitHelper helper){
        this.helper = helper;
    }

    @Override
    public void getChatHistory(String targetUserId, int mPage) {
        helper.getChatHistory(targetUserId, mPage)
                .compose(new IoMainScheduler<>())
                .compose(ResponseTransformer.handleResult())
                .subscribe(new BaseResponseCall<BaseCollection<ChatBean>>(mView) {
                    @Override
                    public void onAccept(BaseCollection<ChatBean> chatBeanBaseCollection) {
                        myHandler.post(()->mView.setData(chatBeanBaseCollection));
                    }
                }, new BaseResponseCall<Throwable>(mView) {
                    @Override
                    public void onAccept(Throwable throwable) {
                        mView.showError(throwable.getMessage());
                    }
                });
    }

    @Override
    public void compressPhoto(Context mContext, List<String> pahtList) {
        Flowable.just(pahtList)
                .observeOn(Schedulers.io())
                .map(stringList -> Luban.with(mContext).load(stringList).get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseResponseCall<List<File>>(mView) {
                    @Override
                    public void onAccept(List<File> files) {
                        mView.setPhoto(files);
                    }
                });
    }
}
