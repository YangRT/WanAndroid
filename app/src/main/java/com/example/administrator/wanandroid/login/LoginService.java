package com.example.administrator.wanandroid.login;

import com.example.administrator.wanandroid.base.BaseResponseInfo;
import com.example.administrator.wanandroid.net.UrlUtil;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {

    @FormUrlEncoded
    @POST(UrlUtil.loginUrl)
    Observable<BaseResponseInfo> getLoginInfo(@Field("username")String username, @Field("password")String password);
}
