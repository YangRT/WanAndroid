package com.example.administrator.wanandroid.utils;

import io.reactivex.disposables.Disposable;

public interface NetTask<T> {
    public Disposable execute(T data,final TaskCallback callback);
}
