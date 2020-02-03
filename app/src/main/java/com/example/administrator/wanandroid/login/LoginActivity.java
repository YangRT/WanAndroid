package com.example.administrator.wanandroid.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.BaseResponseInfo;
import com.example.administrator.wanandroid.databinding.ActivityLoginBinding;
import com.example.administrator.wanandroid.home.HomeActivity;
import com.example.administrator.wanandroid.net.NetUtil;
import com.example.administrator.wanandroid.net.UrlUtil;
import com.example.administrator.wanandroid.register.RegisterActivity;
import com.example.administrator.wanandroid.utils.BaseDataPreferenceUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLoginBinding binding;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(LoginActivity.this,R.layout.activity_login);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        binding.btLogin.setOnClickListener(this);
        binding.tvToRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                if(binding.editUsername.getText().toString().equals("") || binding.editPassword.getText().toString().equals("")){
                    Toast.makeText(this,"请填齐信息！",Toast.LENGTH_LONG).show();
                }else {
                    login(binding.editUsername.getText().toString(),binding.editPassword.getText().toString());
                }
                break;
            case R.id.tv_to_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void login(final String username, String password){
        NetUtil.getInstance().getLoginRetrofitInstance(UrlUtil.baseUrl)
                .create(LoginService.class)
                .getLoginInfo(username,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponseInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(BaseResponseInfo baseResponseInfo) {
                        if(baseResponseInfo.getErrorCode() == 0){
                               Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_LONG).show();
                                BaseDataPreferenceUtil.getInstance().saveLoginStatus(username);
                                loginSuccess();

                        }else {
                            Toast.makeText(LoginActivity.this, baseResponseInfo.getErrorMsg(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this,"网络错误！",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void loginSuccess(){
            Log.e("Login","success");
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(disposable != null){
            disposable.dispose();
        }
    }
}
