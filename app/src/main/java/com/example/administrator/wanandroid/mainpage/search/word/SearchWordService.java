package com.example.administrator.wanandroid.mainpage.search.word;

import com.example.administrator.wanandroid.base.BaseArticleInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SearchWordService {
    @GET("/hotkey/json")
    Observable<SearchWordInfo> getSearchWordInfo();
}
