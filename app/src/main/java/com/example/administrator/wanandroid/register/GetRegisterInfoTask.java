package com.example.administrator.wanandroid.register;


import com.example.administrator.wanandroid.register.model.RegisterInfo;
import com.example.administrator.wanandroid.register.model.RegisterModel;
import com.example.administrator.wanandroid.utils.NetTask;
import com.example.administrator.wanandroid.utils.NetUtil;
import com.example.administrator.wanandroid.utils.TaskCallback;
import com.example.administrator.wanandroid.utils.UrlUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class GetRegisterInfoTask implements NetTask<RegisterModel> {
    private static GetRegisterInfoTask INSTANCE = null;
    private Retrofit retrofit;
    private Disposable disposable;

    public static GetRegisterInfoTask getInstance(){
        if(INSTANCE == null){
            INSTANCE = new GetRegisterInfoTask();
        }
        return INSTANCE;
    }

    public GetRegisterInfoTask(){
        getRetrofit();
    }

    private void getRetrofit(){
        retrofit = NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl);
    }

    @Override
    public Disposable execute(RegisterModel data, final TaskCallback callback) {
        callback.onStart();
        RegisterService service = retrofit.create(RegisterService.class);
        Observable<RegisterInfo> observable = service.getRegisterInfo(data.getUsername(),data.getPassword(),data.getRepassword());
        observable.subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Observer<RegisterInfo>() {
                      @Override
                      public void onSubscribe(Disposable d) {
                          disposable = d;
                      }

                      @Override
                      public void onNext(RegisterInfo registerInfo) {
                            callback.onSuccess(registerInfo);
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
