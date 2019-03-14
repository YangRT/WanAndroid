package com.example.administrator.wanandroid.login;

import android.net.Uri;

import com.example.administrator.wanandroid.login.model.LoginInfo;
import com.example.administrator.wanandroid.login.model.LoginModel;
import com.example.administrator.wanandroid.utils.NetTask;
import com.example.administrator.wanandroid.utils.NetUtil;
import com.example.administrator.wanandroid.utils.TaskCallback;
import com.example.administrator.wanandroid.utils.UrlUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class GetLoginInfoTask implements NetTask<LoginModel> {
    private static GetLoginInfoTask INSTANCE = null;
    private Retrofit retrofit;
    GetLoginInfoTask(){
        createRetrofit();
    }

    private void createRetrofit(){
        retrofit = NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl);
    }
    public static GetLoginInfoTask getInstance(){
        if(INSTANCE == null){
            INSTANCE = new GetLoginInfoTask();
        }
        return INSTANCE;
    }

    private Disposable disposable;
    @Override
    public Disposable execute(LoginModel data, final TaskCallback callback) {
        callback.onStart();
        LoginService service = retrofit.create(LoginService.class);
        Observable<LoginInfo> observable = service.getLoginInfo(data.getUsername(),data.getPassword());
        observable.subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Observer<LoginInfo>() {
                      @Override
                      public void onSubscribe(Disposable d) {
                          disposable = d;
                      }

                      @Override
                      public void onNext(LoginInfo loginInfo) {
                            callback.onSuccess(loginInfo);
                      }

                      @Override
                      public void onError(Throwable e) {
                          if (e.getMessage() != null && !e.getMessage().isEmpty()) {
                              callback.onFailed(e.getMessage());
                          }
                      }

                      @Override
                      public void onComplete() {
                            callback.onFinish();
                      }
                  });

        return disposable;
    }
}
