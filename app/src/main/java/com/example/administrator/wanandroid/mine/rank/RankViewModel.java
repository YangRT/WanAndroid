package com.example.administrator.wanandroid.mine.rank;

import com.example.administrator.wanandroid.base.MvvmBaseViewModel;

public class RankViewModel extends MvvmBaseViewModel<RankModel,RankInfo.Datas> {

    public RankViewModel(){
        super();
        model = new RankModel();
        model.register(this);
        model.getCachedDataAndLoad();
    }

    public void tryToLoadNextPage(){
        model.loadNextPage();
    }

}
