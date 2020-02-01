package com.example.administrator.wanandroid.mainpage.search.word;

import com.example.administrator.wanandroid.base.MvvmBaseViewModel;

public class SearchWordViewModel extends MvvmBaseViewModel<SearchWordModel,SearchWordInfo.Data> {

    public SearchWordViewModel(){
        super();
        model = new SearchWordModel();
        model.register(this);
        model.getCachedDataAndLoad();
    }
}
