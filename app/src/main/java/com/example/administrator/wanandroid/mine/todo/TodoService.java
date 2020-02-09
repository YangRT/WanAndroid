package com.example.administrator.wanandroid.mine.todo;

import com.example.administrator.wanandroid.base.BaseResponseInfo;
import com.example.administrator.wanandroid.mine.rank.RankInfo;
import com.example.administrator.wanandroid.mine.todo.TodoInfo;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface TodoService {

    @GET("lg/todo/v2/list/{path}/json")
    Observable<TodoInfo> getTodoInfo(@Path("path")int path, @QueryMap Map<String,Integer> map);

    @POST("lg/todo/delete/{path}/json")
    Observable<BaseResponseInfo> getDeleteResponse(@Path("path")int id);

    @FormUrlEncoded
    @POST("/lg/todo/done/{path}/json")
    Observable<BaseResponseInfo> getChangeStatusResponse(@Path("path")int id,@Field("status")int status);

    @FormUrlEncoded
    @POST("lg/todo/add/json")
    Observable<BaseResponseInfo> getAddEventResponse(@Field("title")String title,@Field("content")String content,@Field("date")String date,@Field("type")int type);

    @FormUrlEncoded
    @POST("lg/todo/update/{path}/json")
    Observable<BaseResponseInfo> getUpdateEventResponse(@Path("path")int id,@Field("title")String title,@Field("content")String content,@Field("date")String date,@Field("type")int type);
}
