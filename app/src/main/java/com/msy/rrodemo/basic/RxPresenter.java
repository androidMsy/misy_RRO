package com.msy.rrodemo.basic;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2019/9/26/026.
 */

public class RxPresenter<T extends BaseView> implements BasePresenter<T> {

    protected T mView;
    private CompositeDisposable mCompositeDisposable;

    private void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    /**
     * 删除
     *
     * @param disposable disposable
     */
    protected boolean remove(Disposable disposable) {
        return mCompositeDisposable != null && mCompositeDisposable.remove(disposable);
    }

    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }
}
