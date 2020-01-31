package com.example.administrator.wanandroid.mine.article;

import com.example.administrator.wanandroid.base.BaseCustomViewModel;
import com.example.administrator.wanandroid.base.MvvmBaseViewModel;

public class MyArticleViewModel extends MvvmBaseViewModel<MyArticleModel, BaseCustomViewModel> {

    public MyArticleViewModel(String key){
        super();
        model = new MyArticleModel(key);
        model.register(this);
        model.getCachedDataAndLoad();
    }

    public void tryToLoadNextPage(){
        model.loadNextPage();
    }

}
