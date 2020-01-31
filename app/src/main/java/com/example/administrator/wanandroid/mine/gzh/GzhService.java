package com.example.administrator.wanandroid.mine.gzh;

import com.example.administrator.wanandroid.base.BaseArticleInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GzhService {

    @GET("/wxarticle/list/{id}/{path}/json")
    Observable<BaseArticleInfo> getGzhInfo(@Path("id")int id,@Path("path")int path);
}
