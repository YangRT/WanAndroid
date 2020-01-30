package com.example.administrator.wanandroid.mine.point;

import com.example.administrator.wanandroid.base.MvvmBaseViewModel;

public class PointViewModel extends MvvmBaseViewModel<PointModel,PointInfo.Datas> {

    public PointViewModel(){
        super();
        model = new PointModel();
        model.register(this);
        model.getCachedDataAndLoad();
    }

    public void tryToLoadNextPage(){
        model.loadNextPage();
    }

}
