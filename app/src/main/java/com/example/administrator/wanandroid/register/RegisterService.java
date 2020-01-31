package com.example.administrator.wanandroid.register;

import com.example.administrator.wanandroid.base.BaseResponseInfo;
import com.example.administrator.wanandroid.net.UrlUtil;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterService {

    @FormUrlEncoded
    @POST(UrlUtil.registerUrl)
    Observable<BaseResponseInfo> getLoginInfo(@Field("username")String username, @Field("password")String password, @Field("repassword")String repassword);
}
