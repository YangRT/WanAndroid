package com.example.administrator.wanandroid.project.classic;

import com.example.administrator.wanandroid.base.BaseCustomViewModel;
import com.example.administrator.wanandroid.base.MvvmBaseViewModel;

public class ClassicViewModel extends MvvmBaseViewModel<ClassicModel, BaseCustomViewModel> {

    public ClassicViewModel(String key,int id){
        super();
        model = new ClassicModel(key, id);
        model.register(this);
    }

    public void tryToLoadNextPage(){
        model.loadNextPage();
    }

    public void beginLoading(){
        model.getCachedDataAndLoad();
    }

}
