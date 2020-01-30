package com.example.administrator.wanandroid.mine.knowledge;

import com.example.administrator.wanandroid.base.BaseArticleInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface KnowledgeService {

    @GET("/tree/json")
    Observable<KnowledgeInfo> getKnowledgeInfo();

}
