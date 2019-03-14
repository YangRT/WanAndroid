package com.example.administrator.wanandroid.home.homepage;

import com.example.administrator.wanandroid.home.homepage.model.ArticleInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ArticleService {
    @GET("article/list/{path}/json")
    Observable<ArticleInfo> getArticleInfo(@Path("path")int path);
}
