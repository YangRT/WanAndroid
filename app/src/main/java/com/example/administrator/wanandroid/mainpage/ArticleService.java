package com.example.administrator.wanandroid.mainpage;

import com.example.administrator.wanandroid.base.BaseArticleInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ArticleService {
    @GET("article/list/{path}/json")
    Observable<BaseArticleInfo> getArticleInfo(@Path("path")int path);
}
