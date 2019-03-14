package com.example.administrator.wanandroid.home.homepage;

import com.example.administrator.wanandroid.home.homepage.model.BannerInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface BannerService {
    @GET("banner/json")
    Observable<BannerInfo> getBannerInfo();
}
