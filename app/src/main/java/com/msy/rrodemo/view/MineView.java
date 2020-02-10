package com.msy.rrodemo.view;

import com.msy.rrodemo.basic.BasePresenter;
import com.msy.rrodemo.basic.BaseView;
import com.msy.rrodemo.entity.UserBean;

/**
 * Created by Administrator on 2019/12/24/024.
 */

public interface MineView {
    interface View extends BaseView{
        void setData(UserBean bean);
        void loginoutSuccess();
    }
    interface Presenter<T> extends BasePresenter<T>{
        void getUser();
        void loginout();
    }
}
