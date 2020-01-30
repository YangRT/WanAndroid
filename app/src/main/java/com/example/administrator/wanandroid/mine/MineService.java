package com.example.administrator.wanandroid.mine;

import com.example.administrator.wanandroid.base.BaseArticleInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MineService {

    @GET("lg/coin/userinfo/json")
    Observable<MineInfo> getMineInfo();
}
