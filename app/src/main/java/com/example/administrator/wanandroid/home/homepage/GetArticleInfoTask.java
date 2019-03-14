package com.example.administrator.wanandroid.home.homepage;

import com.example.administrator.wanandroid.home.homepage.model.ArticleInfo;
import com.example.administrator.wanandroid.register.GetRegisterInfoTask;
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

public class GetArticleInfoTask implements NetTask<Integer> {
    private static  GetArticleInfoTask INSTANCE = null;
    private Retrofit retrofit;
    private Disposable disposable;
    public static GetArticleInfoTask getInstance(){
        if (INSTANCE == null){
            INSTANCE = new GetArticleInfoTask();
        }
        return INSTANCE;
    }

    public GetArticleInfoTask(){
        getRetrofit();
    }

    private void getRetrofit(){
        retrofit = NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl);
    }
    @Override
    public Disposable execute(Integer data, final TaskCallback callback) {
        callback.onStart();
        ArticleService service = retrofit.create(ArticleService.class);
        Observable<ArticleInfo> observable = service.getArticleInfo(data);
        observable.subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Observer<ArticleInfo>() {
                      @Override
                      public void onSubscribe(Disposable d) {
                          disposable = d;
                      }

                      @Override
                      public void onNext(ArticleInfo articleInfo) {
                        callback.onSuccess(articleInfo);
                      }

                      @Override
                      public void onError(Throwable e) {
                        if (e.getMessage() !=null && !e.getMessage().isEmpty()){
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
