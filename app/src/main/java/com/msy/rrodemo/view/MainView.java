package com.msy.rrodemo.view;

import com.msy.rrodemo.basic.BasePresenter;
import com.msy.rrodemo.basic.BaseView;
import com.msy.rrodemo.entity.MainBean;

import java.util.List;

/**
 * Created by Administrator on 2019/9/26/026.
 */

public interface MainView {
    interface View extends BaseView{
        void setData(MainBean str);

    }
    interface Presenter<T> extends BasePresenter<T>{
        void getData();
    }
}
