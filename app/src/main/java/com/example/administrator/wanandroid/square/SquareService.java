package com.example.administrator.wanandroid.square;

import com.example.administrator.wanandroid.base.BaseArticleInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SquareService {
    @GET("/user_article/list/{path}/json")
    Observable<BaseArticleInfo> getSquareArticleInfo(@Path("path")int path);
}
