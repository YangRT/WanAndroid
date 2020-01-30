package com.example.administrator.wanandroid.project;

import com.example.administrator.wanandroid.base.BaseArticleInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProjectService {

    @GET("article/listproject/{path}/json")
    Observable<BaseArticleInfo> getArticleInfo(@Path("path")int path);
}
