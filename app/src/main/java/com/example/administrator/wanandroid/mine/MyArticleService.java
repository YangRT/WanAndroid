package com.example.administrator.wanandroid.mine;

import com.example.administrator.wanandroid.base.BaseArticleInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MyArticleService {

    //页码从 0 开始
    @GET("lg/collect/list/{path}/json")
    Observable<BaseArticleInfo> getCollectInfo(@Path("path")int path);

    //页码从 1 开始
    @GET("user/lg/private_articles/{path}/json")
    Observable<ShareInfo> getShareInfo(@Path("path")int path);
}
