package com.example.administrator.wanandroid.utils;

public interface TaskCallback<T> {
    void onSuccess(T t);
    void onStart();
    void onFailed(String msg);
    void onFinish();
}
