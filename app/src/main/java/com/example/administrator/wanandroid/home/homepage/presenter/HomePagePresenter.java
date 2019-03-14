package com.example.administrator.wanandroid.home.homepage.presenter;

import com.example.administrator.wanandroid.home.homepage.GetArticleInfoTask;
import com.example.administrator.wanandroid.home.homepage.GetBannerInfoTask;
import com.example.administrator.wanandroid.home.homepage.HomePageContract;
import com.example.administrator.wanandroid.home.homepage.model.ArticleInfo;
import com.example.administrator.wanandroid.home.homepage.model.BannerInfo;
import com.example.administrator.wanandroid.utils.NetTask;
import com.example.administrator.wanandroid.utils.TaskCallback;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;

public class HomePagePresenter implements HomePageContract.Presenter {
    private GetBannerInfoTask bannerInfoTask;
    private GetArticleInfoTask articleInfoTask;
    private HomePageContract.View mView;
    private CompositeDisposable compositeDisposable;

    public HomePagePresenter(GetArticleInfoTask articleInfoTask,GetBannerInfoTask bannerInfoTask){
        this.articleInfoTask = articleInfoTask;
        this.bannerInfoTask = bannerInfoTask;
    }
    @Override
    public void getArticleInfo(Integer integer) {
        articleInfoTask.execute(integer, new TaskCallback<ArticleInfo>() {

            @Override
            public void onSuccess(ArticleInfo articleInfo) {
                mView.setArticleInfo(articleInfo);
            }

            @Override
            public void onStart() {
                mView.showLoading();
            }

            @Override
            public void onFailed(String msg) {
                mView.hideLoading();
                mView.showError(msg);
            }

            @Override
            public void onFinish() {
                mView.hideLoading();
            }
        });
    }

    @Override
    public void getBannerInfo() {
        bannerInfoTask.execute(null, new TaskCallback<BannerInfo>() {

            @Override
            public void onSuccess(BannerInfo bannerInfo) {
                mView.setBannerInfo(bannerInfo);
            }

            @Override
            public void onStart() {
                mView.showLoading();
            }

            @Override
            public void onFailed(String msg) {
                mView.hideLoading();
                mView.showError(msg);
            }

            @Override
            public void onFinish() {
                mView.hideLoading();
            }
        });
    }

    @Override
    public void attachView(HomePageContract.View v) {
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
