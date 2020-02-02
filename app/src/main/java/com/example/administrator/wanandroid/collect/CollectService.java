package com.example.administrator.wanandroid.collect;

import com.example.administrator.wanandroid.base.BaseArticleInfo;
import com.example.administrator.wanandroid.base.BaseResponseInfo;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CollectService {

    @POST("/lg/collect/{id}/json")
    Observable<BaseResponseInfo> getCollectResponse(@Path("id")int id);

    @POST("lg/uncollect_originId/{id}/json")
    Observable<BaseResponseInfo> getUnCollectResponse(@Path("id")int id);

    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    Observable<BaseResponseInfo> getUnCollectInMineResponse(@Path("id")int id, @Field("originId")int originId);
}
