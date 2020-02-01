package com.example.administrator.wanandroid.mine.knowledgeitem;

import com.example.administrator.wanandroid.base.BaseArticleInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KIService {

    @GET("/article/list/{path}/json")
    Observable<BaseArticleInfo> getClassicInfo(@Path("path")int path, @Query("cid") String id);
}
