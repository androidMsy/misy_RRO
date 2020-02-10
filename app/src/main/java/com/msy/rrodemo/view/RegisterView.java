package com.msy.rrodemo.view;

import com.msy.rrodemo.basic.BasePresenter;
import com.msy.rrodemo.basic.BaseView;
import com.msy.rrodemo.entity.UserBean;

/**
 * Created by Administrator on 2019/12/20/020.
 */

public interface RegisterView {
    interface View extends BaseView{
        void registerSuccess(UserBean userBean);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void register(String uname, String pswd);
    }
}
