package com.example.administrator.wanandroid.home.projects;

import com.example.administrator.wanandroid.home.projects.model.TitlesInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface TitleService {
    @GET("project/tree/json")
    Observable<TitlesInfo> getTitlesInfo();
}
