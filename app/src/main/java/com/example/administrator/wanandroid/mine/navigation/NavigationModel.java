package com.example.administrator.wanandroid.mine.navigation;

import android.util.Log;

import com.example.administrator.wanandroid.base.MvvmBaseModel;
import com.example.administrator.wanandroid.base.PagingResult;
import com.example.administrator.wanandroid.mine.knowledge.KnowledgeInfo;
import com.example.administrator.wanandroid.net.NetUtil;
import com.example.administrator.wanandroid.net.UrlUtil;
import com.example.administrator.wanandroid.utils.BaseDataPreferenceUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NavigationModel extends MvvmBaseModel<List<NavigationInfo.Data>> {

    public NavigationModel() {
        super(false,"navigation",null);
    }

    @Override
    public void refresh() {
        load();
    }

    @Override
    protected void load() {
        NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl)
                .create(NavigationService.class)
                .getNavigationInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NavigationInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(NavigationInfo navigationInfo) {
                        if(navigationInfo.getErrorCode() == 0){
                            List<NavigationInfo.Data> list = navigationInfo.getData();
                            boolean isEmpty = list.size() == 0;
                            loadSuccess(list);
                        }else {
                            loadFail(navigationInfo.getErrorMsg(),new PagingResult(true,true,false));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("navigation","error");
                        e.printStackTrace();
                        if (e.getMessage() !=null && !e.getMessage().isEmpty()){
                            loadFail(e.getMessage(),new PagingResult(true,true,false));
                        }
                        setFirst(false);

                    }

                    @Override
                    public void onComplete() {
                        setFirst(false);

                    }
                });
    }

    @Override
    protected Type getTClass() {
        return new TypeToken<List<NavigationInfo.Data>>() {
        }.getType();
    }

    @Override
    protected boolean isNeedToUpdate() {
        long time = System.currentTimeMillis() - BaseDataPreferenceUtil.getInstance().getLong(mCachedPreferenceKey);
        if(time/(24*3600*1000) > 10) return true;;
        return false;
    }
}
