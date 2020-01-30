package com.example.administrator.wanandroid.login;

import com.example.administrator.wanandroid.base.BaseArticleInfo;
import com.example.administrator.wanandroid.net.UrlUtil;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginService {

    @FormUrlEncoded
    @POST(UrlUtil.loginUrl)
    Observable<LoginInfo> getLoginInfo(@Field("username")String username,@Field("password")String password);
}
