package com.msy.rrodemo.net;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.msy.rrodemo.api.RROApi;
import com.msy.rrodemo.contacts.UrlContacts;
import com.msy.rrodemo.net.fastjson.FastJsonConvertFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2019/9/26/026.
 */

public class RetrofitManager {

    private static Retrofit baseRetrofit;
    private static final int timeout = 30;

    public RROApi getNetService(){
        return getBaseRetrofit().create(RROApi.class);
    }
    private static Retrofit getBaseRetrofit() {
        if (baseRetrofit == null) {
            synchronized (RetrofitManager.class) {
                if (baseRetrofit == null) {
                    //日志拦截
                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    //参数拦截
                    Interceptor paramInterceptor = chain -> {
                        Request request = chain.request();
                        HttpUrl.Builder addQueryParameter = request.url().newBuilder()
                                .scheme(request.url().scheme())
                                .host(request.url().host());
//                                .addQueryParameter("token", SPUtils.getInstance().getString(AppConstant.TOKEN))
//                                .addQueryParameter("nowVersions", "" + AppUtils.getVersionCode(App.getApplication()))
//                                .addQueryParameter("iphone_type", "IOS");
                        Request build = request.newBuilder().method(request.method(), request
                                .body())
                                .url(addQueryParameter.build())
                                .build();

                        return chain.proceed(build);
                    };
                    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                            .connectTimeout(timeout, TimeUnit.SECONDS)
                            .readTimeout(timeout, TimeUnit.SECONDS)
                            .writeTimeout(timeout, TimeUnit.SECONDS)
                            .addInterceptor(loggingInterceptor)
                            .addInterceptor(paramInterceptor)
                            .build();

                    baseRetrofit = new Retrofit.Builder()
                            .baseUrl(UrlContacts.BASE_URL)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(new FastJsonConvertFactory())
                            .client(okHttpClient)
                            .build();
                }
            }
        }
        return baseRetrofit;
    }

}
