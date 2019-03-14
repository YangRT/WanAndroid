package com.example.administrator.wanandroid.home.homepage;

import com.example.administrator.wanandroid.home.homepage.model.BannerInfo;
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

public class GetBannerInfoTask implements NetTask {
    private static GetBannerInfoTask INSTANCE = null;
    private Retrofit retrofit;
    private Disposable disposable;

    public GetBannerInfoTask(){
        getRetrofit();
    }

    public static GetBannerInfoTask getInstance(){
        if(INSTANCE == null){
            INSTANCE = new GetBannerInfoTask();
        }
        return INSTANCE;
    }

    private void getRetrofit(){
        retrofit = NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl);
    }
    @Override
    public Disposable execute(Object data, final TaskCallback callback) {
        callback.onStart();
        BannerService service = retrofit.create(BannerService.class);
        Observable<BannerInfo> observable = service.getBannerInfo();
        observable.subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Observer<BannerInfo>() {
                      @Override
                      public void onSubscribe(Disposable d) {
                            disposable = d;
                      }

                      @Override
                      public void onNext(BannerInfo bannerInfo) {
                            callback.onSuccess(bannerInfo);
                      }

                      @Override
                      public void onError(Throwable e) {
                            if(e.getMessage() != null && !e.getMessage().isEmpty()){
                                callback.onFailed(e.toString());
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
