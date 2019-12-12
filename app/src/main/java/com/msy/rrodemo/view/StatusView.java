package com.msy.rrodemo.view;

import com.msy.rrodemo.basic.BasePresenter;
import com.msy.rrodemo.basic.BaseView;
import com.msy.rrodemo.entity.StatusBean;

/**
 * Created by Administrator on 2019/10/17/017.
 */

public interface StatusView {
    interface View extends BaseView{
        void setData(String bean);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void getData(String url);
    }
}
