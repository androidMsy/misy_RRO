package com.msy.rrodemo.api;


import com.alibaba.fastjson.JSONObject;
import com.msy.rrodemo.entity.BaseCollection;
import com.msy.rrodemo.entity.BaseRsp;
import com.msy.rrodemo.entity.ChatBean;
import com.msy.rrodemo.entity.UserBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2019/9/26/026.
 */

public interface RROApi {

    @POST("/user/login")
    Observable<BaseRsp<UserBean>> login(@Body JSONObject object);

    @GET("/user/loginout")
    Observable<BaseRsp<String>> loginout();

    @POST("/user/register")
    Observable<BaseRsp<UserBean>> register(@Body JSONObject object);

    @GET("/user/getUser")
    Observable<BaseRsp<UserBean>> getUser();

    @GET("/user/getAllUser")
    Observable<BaseRsp<BaseCollection<UserBean>>> getAllContacts(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    @GET("/chat/getChatHistory")
    Observable<BaseRsp<BaseCollection<ChatBean>>> getChatHistory(@Query("targetUserId") String targetUserId
            , @Query("pageNum") int pageNum
            , @Query("pageSize") int pageSize);

}
