package com.example.administrator.wanandroid.base;

import io.reactivex.disposables.Disposable;

public interface BasePresenter<T extends BaseView> {
    public void attachView(T v);
    public void detachView();
    public void subscribe(Disposable disposable);
    public void unSubscribe();
}
