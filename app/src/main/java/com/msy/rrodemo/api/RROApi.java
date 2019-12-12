package com.msy.rrodemo.api;


import com.msy.rrodemo.entity.BaseRsp;
import com.msy.rrodemo.entity.MainBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2019/9/26/026.
 */

public interface RROApi {

    @GET("/user/getAllUser")
    Observable<BaseRsp<MainBean>> getData();
}
