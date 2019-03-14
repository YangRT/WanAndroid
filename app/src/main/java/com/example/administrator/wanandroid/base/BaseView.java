package com.example.administrator.wanandroid.base;

public interface BaseView<T> {
    void setPresenter(T presenter);
    void showLoading();
    void showError(String e);
    void hideLoading();
}
