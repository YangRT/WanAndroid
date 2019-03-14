package com.example.administrator.wanandroid.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.login.GetLoginInfoTask;
import com.example.administrator.wanandroid.login.LoginContract;
import com.example.administrator.wanandroid.login.model.LoginInfo;
import com.example.administrator.wanandroid.login.presenter.LoginPresenter;

public class BaseActivity extends AppCompatActivity implements LoginContract.View {
    GetLoginInfoTask task;
    LoginPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void setLoginInfo(LoginInfo info) {

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError(String e) {

    }

    @Override
    public void hideLoading() {

    }
}
