package com.msy.rrodemo.net.callback;

import com.msy.rrodemo.basic.BaseView;

import    io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2019/9/26/026.
 */

public abstract class BaseResponseCall<T> implements Consumer<T> {

    private BaseView mView;

    public BaseResponseCall(BaseView mView){
        this.mView = mView;
    }

    public abstract void onAccept(T t);

    @Override
    public void accept(T t) throws Exception {
        if (null == mView)return;
        mView.complete();
        onAccept(t);
    }
}
