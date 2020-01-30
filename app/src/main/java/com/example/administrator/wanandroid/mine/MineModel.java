package com.example.administrator.wanandroid.mine;

import android.util.Log;

import com.example.administrator.wanandroid.base.BaseCustomViewModel;
import com.example.administrator.wanandroid.base.MvvmBaseModel;
import com.example.administrator.wanandroid.net.NetUtil;
import com.example.administrator.wanandroid.net.UrlUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineModel extends MvvmBaseModel<List<MineInfo>> {

    public MineModel() {
        super(false,"mine",null);
    }

    @Override
    public void refresh() {
        Log.e("mineModel","refresh");
        load();
    }

    @Override
    protected void load() {
        NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl)
                .create(MineService.class)
                .getMineInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MineInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(MineInfo mineInfo) {
                        Log.e("Mine Load:",mineInfo.getErrorCode()+"");
                        List<MineInfo> data = new ArrayList<>();
                        data.add(mineInfo);
                        loadSuccess(data,null);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadFail(e.getMessage(),null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected Type getTClass() {
        return new TypeToken<List<MineInfo>>() {
        }.getType();
    }
}
