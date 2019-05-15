package com.example.administrator.wanandroid.article;

import com.example.administrator.wanandroid.base.BasePresenter;
import com.example.administrator.wanandroid.base.BaseView;

public class ArticleContract {
    interface Presenter extends BasePresenter<View>{
        void getArticleInfo();
    }

    interface View extends BaseView<Presenter>{
        void setArticleInfo();
    }
}
