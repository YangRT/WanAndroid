package com.example.administrator.wanandroid.mine.knowledgeitem;

import com.example.administrator.wanandroid.base.BaseCustomViewModel;
import com.example.administrator.wanandroid.base.MvvmBaseViewModel;

public class KIViewModel extends MvvmBaseViewModel<KIModel, BaseCustomViewModel> {

    public KIViewModel(String key, int id){
        super();
        model = new KIModel(key, id);
        model.register(this);
    }

    public void tryToLoadNextPage(){
        model.loadNextPage();
    }

    public void beginLoading(){
        model.getCachedDataAndLoad();
    }
}
