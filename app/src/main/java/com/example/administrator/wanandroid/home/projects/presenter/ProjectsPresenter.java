package com.example.administrator.wanandroid.home.projects.presenter;

import com.example.administrator.wanandroid.home.homepage.GetArticleInfoTask;
import com.example.administrator.wanandroid.home.homepage.GetBannerInfoTask;
import com.example.administrator.wanandroid.home.homepage.HomePageContract;
import com.example.administrator.wanandroid.home.projects.GetTitleInfoTask;
import com.example.administrator.wanandroid.home.projects.ProjectsContract;
import com.example.administrator.wanandroid.home.projects.model.TitlesInfo;
import com.example.administrator.wanandroid.utils.TaskCallback;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ProjectsPresenter implements ProjectsContract.Presenter {
    private GetTitleInfoTask titleInfoTask;
    private ProjectsContract.View mView;
    private CompositeDisposable compositeDisposable;

    public ProjectsPresenter(GetTitleInfoTask titleInfoTask){
        this.titleInfoTask = titleInfoTask;
    }

    @Override
    public void getProjectInfo(Integer integer) {

    }

    @Override
    public void getTitleInfo() {
        Disposable d = titleInfoTask.execute(null, new TaskCallback<TitlesInfo>() {

            @Override
            public void onSuccess(TitlesInfo titlesInfo) {
                mView.setTitleInfo(titlesInfo);
            }

            @Override
            public void onStart() {
                mView.showLoading();
            }

            @Override
            public void onFailed(String msg) {
                mView.showError(msg);
                mView.hideLoading();
            }

            @Override
            public void onFinish() {
                mView.hideLoading();
            }
        });
        subscribe(d);
    }

    @Override
    public void attachView(ProjectsContract.View v) {
        mView = v;
    }

    @Override
    public void detachView() {
        mView = null;
        unSubscribe();
    }

    @Override
    public void subscribe(Disposable disposable) {
        if(compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    public void unSubscribe() {
        if(compositeDisposable != null){
            compositeDisposable.clear();
        }
    }
}
