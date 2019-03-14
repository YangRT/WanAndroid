package com.example.administrator.wanandroid.home.homepage;

import com.example.administrator.wanandroid.base.BasePresenter;
import com.example.administrator.wanandroid.base.BaseView;
import com.example.administrator.wanandroid.home.homepage.model.ArticleInfo;
import com.example.administrator.wanandroid.home.homepage.model.BannerInfo;

public interface HomePageContract {
    interface Presenter extends BasePresenter<View>{
        void getArticleInfo(Integer integer);
        void getBannerInfo();
    }
    interface View extends BaseView<Presenter>{
        void setArticleInfo(ArticleInfo info);
        void setBannerInfo(BannerInfo info);
    }
}
