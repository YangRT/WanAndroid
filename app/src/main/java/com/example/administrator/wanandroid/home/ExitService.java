package com.example.administrator.wanandroid.home;

import com.example.administrator.wanandroid.login.LoginInfo;
import com.example.administrator.wanandroid.mine.rank.RankInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ExitService {

    @GET("user/logout/json")
    Observable<LoginInfo> getExitInfo();
}
