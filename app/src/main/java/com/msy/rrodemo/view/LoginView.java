package com.msy.rrodemo.view;

import com.msy.rrodemo.basic.BasePresenter;
import com.msy.rrodemo.basic.BaseView;
import com.msy.rrodemo.entity.UserBean;

/**
 * Created by Administrator on 2019/12/19/019.
 */

public interface LoginView {
    interface View extends BaseView{
        void loginSuccess(UserBean userBean);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void login(String username, String pswd);
    }
}
