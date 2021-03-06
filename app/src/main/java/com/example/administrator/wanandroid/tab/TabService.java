package com.example.administrator.wanandroid.tab;

import com.example.administrator.wanandroid.base.BaseArticleInfo;
import com.example.administrator.wanandroid.mine.gzh.GzhListInfo;
import com.example.administrator.wanandroid.project.classic.ClassicListInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TabService {

    @GET("/wxarticle/chapters/json")
    Observable<GzhListInfo> getGzhListInfo();

    @GET("/project/tree/json")
    Observable<ClassicListInfo> getClassicListInfo();

}
