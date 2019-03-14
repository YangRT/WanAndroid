package com.example.administrator.wanandroid.login.presenter;


import com.example.administrator.wanandroid.login.GetLoginInfoTask;
import com.example.administrator.wanandroid.login.LoginContract;
import com.example.administrator.wanandroid.login.model.LoginInfo;
import com.example.administrator.wanandroid.login.model.LoginModel;
import com.example.administrator.wanandroid.utils.TaskCallback;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class LoginPresenter implements LoginContract.Presenter,TaskCallback<LoginInfo> {
    private CompositeDisposable compositeDisposable;
    private LoginContract.View taskView;
    private GetLoginInfoTask task;

    public LoginPresenter(GetLoginInfoTask task){
        this.task = task;
    }

    @Override
    public void getLoginInfo(LoginModel model) {
        Disposable disposable = task.execute(model,this);
        subscribe(disposable);
    }


    @Override
    public void attachView(LoginContract.View v) {
        taskView = v;
    }

    @Override
    public void detachView() {
        taskView = null;
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

    @Override
    public void onSuccess(LoginInfo loginInfo) {
        taskView.hideLoading();
        taskView.setLoginInfo(loginInfo);
    }

    @Override
    public void onStart() {
        taskView.showLoading();
    }

    @Override
    public void onFailed(String msg) {
        taskView.hideLoading();
        taskView.showError(msg);
    }

    @Override
    public void onFinish() {
        taskView.hideLoading();
    }
}
