package com.example.administrator.wanandroid.login;

import com.example.administrator.wanandroid.base.BasePresenter;
import com.example.administrator.wanandroid.base.BaseView;
import com.example.administrator.wanandroid.login.model.LoginInfo;
import com.example.administrator.wanandroid.login.model.LoginModel;

import io.reactivex.disposables.Disposable;

public interface LoginContract {
    interface Presenter extends BasePresenter<View> {
        void getLoginInfo(LoginModel model);
    }
    interface View extends BaseView<Presenter>{
        void setLoginInfo(LoginInfo info);
    }
}
