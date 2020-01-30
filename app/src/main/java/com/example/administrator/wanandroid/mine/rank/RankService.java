package com.example.administrator.wanandroid.mine.rank;

import com.example.administrator.wanandroid.mine.MineInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RankService {
    @GET("coin/rank/{path}/json")
    Observable<RankInfo> getRankInfo(@Path("path")int path);
}
