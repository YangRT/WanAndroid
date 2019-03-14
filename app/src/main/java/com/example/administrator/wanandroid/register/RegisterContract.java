package com.example.administrator.wanandroid.register;

import com.example.administrator.wanandroid.base.BasePresenter;
import com.example.administrator.wanandroid.base.BaseView;
import com.example.administrator.wanandroid.register.model.RegisterInfo;
import com.example.administrator.wanandroid.register.model.RegisterModel;

public interface RegisterContract {
    interface Presenter extends BasePresenter<View>{
        void getRegisterInfo(RegisterModel model);
    }
    interface View extends BaseView<Presenter> {
        void setRegisterInfo(RegisterInfo info);
    }
}
