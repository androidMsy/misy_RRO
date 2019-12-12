package com.msy.rrodemo.basic;

/**
 * Created by Administrator on 2019/9/26/026.
 */

public interface BasePresenter<T> {

    void attachView(T view);

    void detachView();

}
