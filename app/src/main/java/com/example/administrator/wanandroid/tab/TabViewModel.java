package com.example.administrator.wanandroid.tab;

import com.example.administrator.wanandroid.base.MvvmBaseViewModel;
import com.example.administrator.wanandroid.mine.gzh.GzhListInfo;

public class TabViewModel extends MvvmBaseViewModel<TabModel, GzhListInfo> {

    public TabViewModel(String type){
        model = new TabModel(type);
        model.register(this);
        model.getCachedDataAndLoad();
    }
}
