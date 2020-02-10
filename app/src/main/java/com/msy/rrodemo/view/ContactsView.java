package com.msy.rrodemo.view;

/**
 * Created by Administrator on 2019/12/23/023.
 */

import com.msy.rrodemo.basic.BasePresenter;
import com.msy.rrodemo.basic.BaseView;
import com.msy.rrodemo.entity.BaseCollection;
import com.msy.rrodemo.entity.UserBean;

public interface ContactsView {
    interface View extends BaseView{
        void setData(BaseCollection<UserBean> mList);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void getAllContacts(int mPage);
    }
}
