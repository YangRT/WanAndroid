package com.example.administrator.wanandroid.register;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.databinding.ActivityRegisterBinding;
import com.example.administrator.wanandroid.base.BaseResponseInfo;
import com.example.administrator.wanandroid.home.HomeActivity;
import com.example.administrator.wanandroid.login.LoginActivity;
import com.example.administrator.wanandroid.net.NetUtil;
import com.example.administrator.wanandroid.net.UrlUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityRegisterBinding binding;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_register:
                if(binding.editUsername.getText().toString().equals("") || binding.editPassword.getText().toString().equals("") || binding.editRePassword.getText().toString().equals("")){
                    Toast.makeText(this,"请填齐信息！",Toast.LENGTH_SHORT).show();
                }else {
                    register(binding.editUsername.getText().toString(),binding.editPassword.getText().toString(),binding.editRePassword.getText().toString());

                }
                break;
        }
    }

    private void register(String username,String password,String repassword){
        NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl)
                .create(RegisterService.class)
                .getLoginInfo(username,password,repassword)
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
                            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                            finish();

                        }else {
                            Toast.makeText(RegisterActivity.this,baseResponseInfo.getErrorMsg(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this,"网络错误！",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(disposable != null){
            disposable.dispose();
        }
    }
}
