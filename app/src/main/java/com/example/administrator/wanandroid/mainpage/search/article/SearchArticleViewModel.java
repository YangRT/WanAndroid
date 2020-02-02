package com.example.administrator.wanandroid.mainpage.search.article;

import com.example.administrator.wanandroid.base.BaseCustomViewModel;
import com.example.administrator.wanandroid.base.MvvmBaseViewModel;

public class SearchArticleViewModel extends MvvmBaseViewModel<SearchArticleModel, BaseCustomViewModel> {

    public SearchArticleViewModel(String key){
        super();
        model = new SearchArticleModel(key);
        model.register(this);
        model.getCachedDataAndLoad();
    }

    public void tryToLoadNextPage(){
        model.loadNextPage();
    }
}
