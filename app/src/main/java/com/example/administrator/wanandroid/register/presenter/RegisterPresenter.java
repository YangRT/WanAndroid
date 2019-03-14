package com.example.administrator.wanandroid.register.presenter;

import com.example.administrator.wanandroid.register.GetRegisterInfoTask;
import com.example.administrator.wanandroid.register.model.RegisterInfo;
import com.example.administrator.wanandroid.register.model.RegisterModel;
import com.example.administrator.wanandroid.register.RegisterContract;
import com.example.administrator.wanandroid.utils.TaskCallback;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RegisterPresenter implements RegisterContract.Presenter,TaskCallback<RegisterInfo> {
    private GetRegisterInfoTask task;
    private RegisterContract.View taskView;
    private CompositeDisposable compositeDisposable;

    public RegisterPresenter(GetRegisterInfoTask task){
        this.task = task;
    }

    @Override
    public void getRegisterInfo(RegisterModel model) {
        task.execute(model,this);
    }

    @Override
    public void attachView(RegisterContract.View v) {
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
    public void onSuccess(RegisterInfo registerInfo) {
            taskView.hideLoading();
            taskView.setRegisterInfo(registerInfo);
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
