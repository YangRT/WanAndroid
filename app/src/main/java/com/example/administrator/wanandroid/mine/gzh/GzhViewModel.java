package com.example.administrator.wanandroid.mine.gzh;

import com.example.administrator.wanandroid.base.BaseCustomViewModel;
import com.example.administrator.wanandroid.base.MvvmBaseViewModel;

public class GzhViewModel extends MvvmBaseViewModel<GzhModel, BaseCustomViewModel> {

    public GzhViewModel(String key,int id){
        model = new GzhModel(key, id);
        model.register(this);

    }

    public void tryToLoadNextPage(){
        model.loadNextPage();
    }

    public void beginLoading(){
        model.getCachedDataAndLoad();
    }
}

