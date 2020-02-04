package com.example.administrator.wanandroid.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

// json 处理及生成
public class GsonUtil {

    public static <T> String toJson(T data){
        Gson gson = new Gson();
        return gson.toJson(data);
    }

    public static <T> T fromLocalJson(String data,Type clazz){
        Gson gson = new Gson();
        return gson.fromJson(data,clazz);
    }
}
