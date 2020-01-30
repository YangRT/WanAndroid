package com.example.administrator.wanandroid.mainpage;

public interface IBannerListener{
    void loadSuccess(BannerInfo bannerInfo);
    void loadFail(String msg);
}
