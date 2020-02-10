package com.msy.rrodemo.net;


import com.msy.rrodemo.api.RROApi;
import com.msy.rrodemo.entity.BaseCollection;
import com.msy.rrodemo.entity.BaseRsp;
import com.msy.rrodemo.entity.ChatBean;
import com.msy.rrodemo.entity.UserBean;
import com.msy.rrodemo.utils.JsonUtils;

import io.reactivex.Observable;

import static com.msy.rrodemo.contacts.UrlContacts.PAGE_SIZE;


/**
 * Created by Administrator on 2019/9/27/027.
 */

public class RetrofitHelper {
    private final RROApi api;

    public RetrofitHelper(RROApi api) {
        this.api = api;
    }

    /**
     * 登录
     * @return
     */
    public Observable<BaseRsp<UserBean>> login(String uname,String pswd){
        return api.login(JsonUtils.getRegisterAndLoginJson(uname, pswd));
    }

    /**
     * 登出
     * @return
     */
    public Observable<BaseRsp<String>> loginout(){
        return api.loginout();
    }
    /**
     * 注册
     * @param uname
     * @param pswd
     * @return
     */
    public Observable<BaseRsp<UserBean>> register(String uname, String pswd){
        return api.register(JsonUtils.getRegisterAndLoginJson(uname, pswd));
    }

    public Observable<BaseRsp<UserBean>> getUser(){
        return api.getUser();
    }

    /**
     * 获取所有联系人
     * @return
     */
    public Observable<BaseRsp<BaseCollection<UserBean>>> getAllContacts(int pageNum){
        return api.getAllContacts(pageNum, PAGE_SIZE);
    }

    /**
     * 获取聊天记录
     * @param targetUserId
     * @param mPage
     * @return
     */
    public Observable<BaseRsp<BaseCollection<ChatBean>>> getChatHistory(String targetUserId, int mPage){
        return api.getChatHistory(targetUserId, mPage, PAGE_SIZE);
    }

}
