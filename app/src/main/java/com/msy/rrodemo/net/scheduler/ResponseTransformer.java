package com.msy.rrodemo.net.scheduler;


import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.msy.rrodemo.entity.BaseCollection;
import com.msy.rrodemo.entity.BaseRsp;
import com.msy.rrodemo.entity.UserBean;
import com.msy.rrodemo.net.exception.ApiException;
import com.msy.rrodemo.net.exception.ErrorStatus;
import com.msy.rrodemo.net.exception.ExceptionHandle;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

public class ResponseTransformer {


    public static <T> ObservableTransformer<BaseRsp<T>, T> handleResult() {
        return upstream -> upstream
                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction<>());
    }

    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends BaseRsp<T>>> {
        @Override
        public ObservableSource<? extends BaseRsp<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(new Throwable(ExceptionHandle.handleException(throwable)));
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<BaseRsp<T>, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(BaseRsp<T> tResponse) throws Exception {
            int code = tResponse.getStatus();
            String message = tResponse.getMsg();
            if (code == ErrorStatus.SUCCESS) {
                return Observable.just(tResponse.getData());
            } else {
                return Observable.error(new Throwable(ExceptionHandle.handleException(new ApiException(message))));
            }
        }
    }
}
