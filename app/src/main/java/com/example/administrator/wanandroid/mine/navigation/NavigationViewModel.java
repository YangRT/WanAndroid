package com.example.administrator.wanandroid.mine.navigation;

import com.example.administrator.wanandroid.base.MvvmBaseViewModel;

public class NavigationViewModel extends MvvmBaseViewModel<NavigationModel,NavigationInfo.Data> {

    public NavigationViewModel(){
        super();
        model = new NavigationModel();
        model.register(this);
        model.getCachedDataAndLoad();
    }
}
