package com.example.administrator.wanandroid.square.share;

import com.example.administrator.wanandroid.base.BaseResponseInfo;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ShareService {

    @FormUrlEncoded
    @POST("lg/user_article/add/json")
    Observable<BaseResponseInfo> getShareArticleInfo(@Field("title")String title,@Field("link")String link);
}
