package com.example.administrator.wanandroid.utils;

import com.example.administrator.wanandroid.register.GetRegisterInfoTask;
import com.example.administrator.wanandroid.register.RegisterService;
import com.example.administrator.wanandroid.register.model.RegisterInfo;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Task<T> {
    private static Task INSTANCE = null;
    private Retrofit retrofit;
    private Disposable disposable;

    public static Task getInstance(){
        if(INSTANCE == null){
            INSTANCE = new Task();
        }
        return INSTANCE;
    }

    public Task(){
        getRetrofit();
    }

    private void getRetrofit(){
        retrofit = NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl);
    }

    <E> Disposable execute(E data,final TaskCallback callback){


        return disposable;
    }
}
