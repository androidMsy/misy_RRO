package com.msy.rrodemo.view;

import android.content.Context;

import com.msy.rrodemo.basic.BasePresenter;
import com.msy.rrodemo.basic.BaseView;
import com.msy.rrodemo.entity.BaseCollection;
import com.msy.rrodemo.entity.ChatBean;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2019/12/16/016.
 */

public interface ChatView {
    interface View extends BaseView{
        void setData(BaseCollection<ChatBean> list);
        void setPhoto(List<File> fileList);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void getChatHistory(String targetUserId, int mPage);
        void compressPhoto(Context mContext, List<String> pahtList);
    }
}
