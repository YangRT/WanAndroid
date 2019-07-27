package com.example.administrator.wanandroid.home.projects;

import com.example.administrator.wanandroid.base.BasePresenter;
import com.example.administrator.wanandroid.base.BaseView;
import com.example.administrator.wanandroid.home.homepage.model.ArticleInfo;
import com.example.administrator.wanandroid.home.projects.model.ProjectsInfo;
import com.example.administrator.wanandroid.home.projects.model.TitlesInfo;

public interface ProjectsContract {
    interface Presenter extends BasePresenter<ProjectsContract.View> {
        void getProjectInfo(Integer integer);
        void getTitleInfo();
    }
    interface View extends BaseView<ProjectsContract.Presenter> {
        void setProjectInfo(ProjectsInfo info);
        void setTitleInfo(TitlesInfo info);
    }
}
