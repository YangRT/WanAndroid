package com.example.administrator.wanandroid.net;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetUtil {
    private static NetUtil INSTANCE = null;

    public static NetUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NetUtil();
        }
        return INSTANCE;
    }

    public OkHttpClient getClientInstance(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new AddCookiesInterceptor());
        return builder.build();
    }

    public OkHttpClient getLoginClientInstance(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new SaveCookiesInterceptor());
        builder.addInterceptor(new AddCookiesInterceptor());
        return builder.build();
    }

    public Retrofit getLoginRetrofitInstance(String url){
        OkHttpClient client = getLoginClientInstance();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url).addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }
    public Retrofit getRetrofitInstance(String url) {
        OkHttpClient client = getClientInstance();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url).addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }
}
