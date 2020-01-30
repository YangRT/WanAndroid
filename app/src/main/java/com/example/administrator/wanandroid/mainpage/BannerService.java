package com.example.administrator.wanandroid.mainpage;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface BannerService {
    @GET("/banner/json")
    Observable<BannerInfo> getBannerInfo();
}
