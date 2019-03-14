package com.example.administrator.wanandroid.register;

import com.example.administrator.wanandroid.register.model.RegisterInfo;
import com.example.administrator.wanandroid.utils.UrlUtil;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterService {
    @FormUrlEncoded
    @POST(UrlUtil.registerUrl)
    Observable<RegisterInfo> getRegisterInfo(@Field("username")String username,@Field("password")String password,@Field("repassword")String repassword);
}
