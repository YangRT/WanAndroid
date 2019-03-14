package com.example.administrator.wanandroid.register.view;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.login.view.LoginActivity;
import com.example.administrator.wanandroid.register.GetRegisterInfoTask;
import com.example.administrator.wanandroid.register.RegisterContract;
import com.example.administrator.wanandroid.register.model.RegisterInfo;
import com.example.administrator.wanandroid.register.model.RegisterModel;
import com.example.administrator.wanandroid.register.presenter.RegisterPresenter;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View,View.OnClickListener {
    RegisterContract.Presenter mPresenter;
    RegisterPresenter registerPresenter;
    GetRegisterInfoTask task;
    Dialog dialog;
    EditText editUsername;
    EditText editPassword;
    EditText editRePassword;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init(){
        task = GetRegisterInfoTask.getInstance();
        registerPresenter = new RegisterPresenter(task);
        dialog = new Dialog(this);
        dialog.setTitle("注册中");
        editUsername = findViewById(R.id.edit_register_username);
        editPassword = findViewById(R.id.edit_register_password);
        editRePassword = findViewById(R.id.edit_register_repassword);
        btnRegister = findViewById(R.id.button_register);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_register:
                RegisterModel model = new RegisterModel();
                model.setUsername(editUsername.getText().toString());
                model.setPassword(editPassword.getText().toString());
                model.setRepassword(editRePassword.getText().toString());
                setPresenter(registerPresenter);
                mPresenter.attachView(this);
                mPresenter.getRegisterInfo(model);
                break;
        }
    }

    @Override
    public void setRegisterInfo(RegisterInfo info) {
        if(info.getErrorCode() == 0){
            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this,info.getErrorMsg(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading() {
        dialog.show();
    }

    @Override
    public void showError(String e) {
        Toast.makeText(this,e,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        if(dialog.isShowing()){
            dialog.dismiss();
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
