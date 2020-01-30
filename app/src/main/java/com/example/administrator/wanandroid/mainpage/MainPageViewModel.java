package com.example.administrator.wanandroid.mainpage;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.administrator.wanandroid.base.BaseCustomViewModel;
import com.example.administrator.wanandroid.base.IBaseModelListener;
import com.example.administrator.wanandroid.base.MvvmBaseModel;
import com.example.administrator.wanandroid.base.MvvmBaseViewModel;
import com.example.administrator.wanandroid.base.PagingResult;

public class MainPageViewModel extends MvvmBaseViewModel<ArticleModel, BaseCustomViewModel> {

    static MutableLiveData<BannerInfo> bannerData = new MutableLiveData<>();

    protected BannerModel bannerModel;
    private BannerListener mListener;

    public MainPageViewModel(){
        super();
        model = new ArticleModel("all");
        bannerModel = new BannerModel();
        model.register(this);
        mListener = new BannerListener();
        bannerModel.register(mListener);
        bannerModel.getCachedDataAndLoad();
        model.getCachedDataAndLoad();
    }

    public void tryToLoadNextPage(){
        model.loadNextPage();
    }

    private static class BannerListener implements IBaseModelListener<BannerInfo>{

        @Override
        public void loadFinish(MvvmBaseModel viewModel, BannerInfo data, PagingResult... results) {
            Log.e("Banner","loadFinish");
            if(data != null){
                bannerData.postValue(data);
            }
        }

        @Override
        public void loadFail(MvvmBaseModel viewModel, String msg, PagingResult... results) {
            Log.e("Banner","loadFail");
            errorMsg.postValue(msg);
        }
    }

}
