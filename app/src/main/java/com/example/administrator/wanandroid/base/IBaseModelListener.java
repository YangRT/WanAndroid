package com.example.administrator.wanandroid.base;

public interface IBaseModelListener<T> {

    void loadFinish(MvvmBaseModel viewModel, T data, PagingResult... results);

    void loadFail(MvvmBaseModel viewModel,String msg,PagingResult... results);
}
