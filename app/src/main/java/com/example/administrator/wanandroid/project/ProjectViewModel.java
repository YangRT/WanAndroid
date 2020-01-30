package com.example.administrator.wanandroid.project;

import com.example.administrator.wanandroid.base.BaseCustomViewModel;
import com.example.administrator.wanandroid.base.MvvmBaseViewModel;

public class ProjectViewModel extends MvvmBaseViewModel<ProjectModel, BaseCustomViewModel> {

    public ProjectViewModel(){
        super();
        model = new ProjectModel();
        model.register(this);
        model.getCachedDataAndLoad();
    }

    public void tryToLoadNextPage(){
        model.loadNextPage();
    }
}
