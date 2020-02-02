package com.example.administrator.wanandroid.mainpage.search.article;

import com.example.administrator.wanandroid.base.BaseArticleInfo;
import com.example.administrator.wanandroid.mainpage.search.word.SearchWordInfo;
import com.example.administrator.wanandroid.net.UrlUtil;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SearchArticleService {

    @FormUrlEncoded
    @POST("/article/query/{path}/json")
    Observable<BaseArticleInfo> getSearchArticleInfo(@Path("path")int path, @Field("k")String key);
}
