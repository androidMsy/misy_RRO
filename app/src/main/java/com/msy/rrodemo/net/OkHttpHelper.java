package com.msy.rrodemo.net;

import android.content.Context;


import com.msy.rrodemo.app.MyApp;
import com.msy.rrodemo.utils.SPUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.msy.rrodemo.contacts.AboutSPContacts.AUTH_TOKEN;
import static com.msy.rrodemo.contacts.AboutSPContacts.USER_SP_KEY;

/**
 * 描述:okHttp 帮助类
 * 全局统一使用的OkHttpClient工具，okhttp版本：okhttp3
 */
public class OkHttpHelper {
    //读取时间
    private static final long DEFAULT_READ_TIMEOUT_MILLIS = 20 * 1000;
    //写入时间
    private static final long DEFAULT_WRITE_TIMEOUT_MILLIS = 20 * 1000;
    //超时时间
    private static final long DEFAULT_CONNECT_TIMEOUT_MILLIS = 20 * 1000;
    //最大缓存
    private static final long HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 20 * 1024 * 1024;//设置20M
    //长缓存有效期为7天
    private static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;

    private static volatile OkHttpHelper sInstance;

    private OkHttpClient mOkHttpClient;
    private Context mContext = MyApp.getApplication();

    private OkHttpHelper() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        //包含header、body数据
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //参数拦截
        Interceptor paramInterceptor = chain -> {
            Request request = chain.request();
            HttpUrl.Builder addQueryParameter = request.url().newBuilder()
                    .scheme(request.url().scheme())
                    .host(request.url().host());
            Request build = request.newBuilder().method(request.method(), request
                    .body())
                    .addHeader("Cookie", "JSESSIONID=" + SPUtils.getInstance(USER_SP_KEY).getString(AUTH_TOKEN))
                    .url(addQueryParameter.build())
                    .build();
            return chain.proceed(build);
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.networkInterceptors().add(chain -> {
//            Response originalResponse = chain.proceed(chain.request());
//            return originalResponse
//                    .newBuilder()
//                    .body(new FileResponseBody(originalResponse))//将自定义的ResposeBody设置给它
//                    .build();
//        });

        mOkHttpClient = builder
                .readTimeout(DEFAULT_READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                //设置缓存
//                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
//                .addInterceptor(mRewriteCacheControlInterceptor)
//                FaceBook 网络调试器，可在Chrome调试网络请求，查看SharePreferences,数据库等
//                .addNetworkInterceptor(new StethoInterceptor())
                //http数据log，日志中打印出HTTP请求&响应数据
                .addInterceptor(loggingInterceptor)
                .addInterceptor(paramInterceptor)
                .build();
    }

    public static OkHttpHelper getInstance() {
        if (sInstance == null) {
            synchronized (OkHttpHelper.class) {
                if (sInstance == null) {
                    sInstance = new OkHttpHelper();
                }
            }
        }
        return sInstance;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }
}
