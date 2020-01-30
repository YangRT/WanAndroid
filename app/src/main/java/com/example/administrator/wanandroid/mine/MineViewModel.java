package com.example.administrator.wanandroid.mine;

import com.example.administrator.wanandroid.base.MvvmBaseModel;
import com.example.administrator.wanandroid.base.MvvmBaseViewModel;
import com.example.administrator.wanandroid.base.PagingResult;
import com.example.administrator.wanandroid.viewstatus.ViewStatus;

import java.util.List;

public class MineViewModel extends MvvmBaseViewModel<MineModel,MineInfo> {

    public MineViewModel(){
        model = new MineModel();
        model.register(this);
        model.getCachedDataAndLoad();
    }




    @Override
    public void loadFail(MvvmBaseModel viewModel, String msg, PagingResult... results) {
        errorMsg.postValue(msg);
        viewStatusLiveData.setValue(ViewStatus.LOAD_MORE_FAILED);
    }
}
