package com.example.administrator.wanandroid.home.projects;

import com.example.administrator.wanandroid.home.homepage.GetBannerInfoTask;
import com.example.administrator.wanandroid.home.projects.model.TitlesInfo;
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

public class GetTitleInfoTask implements NetTask {
    private static GetTitleInfoTask INSTANCE = null;
    private Retrofit retrofit;
    private Disposable disposable;

    public GetTitleInfoTask(){
        getRetrofit();
    }

    public static GetTitleInfoTask getInstance(){
        if(INSTANCE == null){
            INSTANCE = new GetTitleInfoTask();
        }
        return INSTANCE;
    }

    private void getRetrofit(){
        retrofit = NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl);
    }


    @Override
    public Disposable execute(Object data,final TaskCallback callback) {
       callback.onStart();
       TitleService service = retrofit.create(TitleService.class);
        Observable<TitlesInfo> observable = service.getTitlesInfo();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TitlesInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(TitlesInfo titlesInfo) {
                        callback.onSuccess(titlesInfo);
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
