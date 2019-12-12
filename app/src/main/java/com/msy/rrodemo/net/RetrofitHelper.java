package com.msy.rrodemo.net;


import com.msy.rrodemo.api.RROApi;
import com.msy.rrodemo.entity.BaseRsp;
import com.msy.rrodemo.entity.MainBean;

import io.reactivex.Observable;


/**
 * Created by Administrator on 2019/9/27/027.
 */

public class RetrofitHelper {
    private final RROApi api;

    public RetrofitHelper(RROApi api) {
        this.api = api;
    }

    public Observable<BaseRsp<MainBean>> getData(){
        return api.getData();
    }

}
