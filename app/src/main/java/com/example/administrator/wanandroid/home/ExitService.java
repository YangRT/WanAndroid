package com.example.administrator.wanandroid.home;

import com.example.administrator.wanandroid.base.BaseResponseInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ExitService {

    @GET("user/logout/json")
    Observable<BaseResponseInfo> getExitInfo();
}
