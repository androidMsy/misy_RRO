package com.msy.rrodemo.di.module;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.msy.rrodemo.net.OkHttpHelper;
import com.msy.rrodemo.api.RROApi;
import com.msy.rrodemo.net.RetrofitHelper;
import com.msy.rrodemo.contacts.UrlContacts;
import com.msy.rrodemo.di.qualifier.AppUrl;
import com.msy.rrodemo.net.fastjson.FastJsonConvertFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2019/9/27/027.
 */

@Module
public class HttpModule {

    public Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        return builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(new FastJsonConvertFactory())
                .build();
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient() {
        return OkHttpHelper.getInstance().getOkHttpClient();
    }

    @Singleton
    @Provides
    public Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    public RetrofitHelper provideRetrofitHelper(RROApi api) {
        return new RetrofitHelper(api);
    }


    @Singleton
    @Provides
    @AppUrl
    public Retrofit provideAppRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, UrlContacts.BASE_URL);
    }

    @Singleton
    @Provides
    public RROApi provideAppService(@AppUrl Retrofit retrofit) {
        return retrofit.create(RROApi.class);
    }
}
