package com.example.administrator.wanandroid.mine.navigation;

import com.example.administrator.wanandroid.mine.knowledge.KnowledgeInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface NavigationService {

    @GET("navi/json")
    Observable<NavigationInfo> getNavigationInfo();

}
