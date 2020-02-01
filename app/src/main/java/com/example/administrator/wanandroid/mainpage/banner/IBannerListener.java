package com.example.administrator.wanandroid.mainpage.banner;

import com.example.administrator.wanandroid.mainpage.banner.BannerInfo;

public interface IBannerListener{
    void loadSuccess(BannerInfo bannerInfo);
    void loadFail(String msg);
}
