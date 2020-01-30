package com.example.administrator.wanandroid.square;

import com.example.administrator.wanandroid.base.BaseCustomViewModel;
import com.example.administrator.wanandroid.base.MvvmBaseViewModel;

public class SquareViewModel extends MvvmBaseViewModel<SquareModel, BaseCustomViewModel> {

    public SquareViewModel(){
        super();
        model = new SquareModel();
        model.register(this);
        model.getCachedDataAndLoad();
    }

    public void tryToLoadNextPage(){
        model.loadNextPage();
    }

}
