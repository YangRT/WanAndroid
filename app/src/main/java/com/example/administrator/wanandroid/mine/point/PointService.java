package com.example.administrator.wanandroid.mine.point;

import com.example.administrator.wanandroid.mine.rank.RankInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PointService {

    @GET("/lg/coin/list/{path}/json")
    Observable<PointInfo> getPointInfo(@Path("path")int path);
}
