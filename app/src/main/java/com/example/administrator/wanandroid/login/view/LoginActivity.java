package com.example.administrator.wanandroid.login.view;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.home.HomeActivity;
import com.example.administrator.wanandroid.login.GetLoginInfoTask;
import com.example.administrator.wanandroid.login.LoginContract;
import com.example.administrator.wanandroid.login.model.LoginInfo;
import com.example.administrator.wanandroid.login.model.LoginModel;
import com.example.administrator.wanandroid.login.presenter.LoginPresenter;
import com.example.administrator.wanandroid.register.view.RegisterActivity;
import com.example.administrator.wanandroid.utils.ConfigUtil;

public class LoginActivity extends AppCompatActivity implements LoginContract.View,View.OnClickListener {
    GetLoginInfoTask task;
    LoginPresenter loginPresenter;
    LoginContract.Presenter  mPresenter;
    Button btnLogin;
    TextView tvForgotPassword;
    TextView tvSignUp;
    EditText editUsername;
    EditText editPassword;
    Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();


    }

    private void init(){
        task = GetLoginInfoTask.getInstance();
        loginPresenter = new LoginPresenter(task);
        mDialog = new Dialog(this);
        mDialog.setTitle("登录中");
        btnLogin = findViewById(R.id.btn_login);
        tvSignUp = findViewById(R.id.tv_sign_up);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);
        editUsername = findViewById(R.id.edit_username);
        editPassword = findViewById(R.id.edit_password);
        btnLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
    }

    @Override
    public void setLoginInfo(LoginInfo info) {
        if(info.getErrorCode() == 0){
            Log.d("Login","Success");
            ConfigUtil.cacheUsername(this,editUsername.getText().toString());
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this,info.getErrorMsg(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading() {
        mDialog.show();
    }

    @Override
    public void showError(String e) {
        Toast.makeText(this,e,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        if (mDialog.isShowing()){
            mDialog.dismiss();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                LoginModel model = new LoginModel();
                model.setUsername(editUsername.getText().toString());
                model.setPassword(editPassword.getText().toString());
                Log.d("",model.getUsername()+" "+model.getPassword());
                setPresenter(loginPresenter);
                mPresenter.attachView(this);
                mPresenter.getLoginInfo(model);
                break;
            case R.id.tv_forgot_password:
                break;
            case R.id.tv_sign_up:
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.detachView();
        }

    }
}
