package com.example.administrator.wanandroid.mainpage;

import android.util.Log;

import com.example.administrator.wanandroid.base.MvvmBaseModel;
import com.example.administrator.wanandroid.base.PagingResult;
import com.example.administrator.wanandroid.net.NetUtil;
import com.example.administrator.wanandroid.net.UrlUtil;
import com.example.administrator.wanandroid.utils.BaseDataPreferenceUtil;

import java.lang.reflect.Type;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BannerModel extends MvvmBaseModel<BannerInfo> {

    public BannerModel() {
        super(false, "banner",null);
    }

    @Override
    public void refresh() {
        load();
    }

    @Override
    protected void load() {
        Log.e("Banner","load");
        NetUtil.getInstance()
                .getRetrofitInstance(UrlUtil.baseUrl)
                .create(BannerService.class)
                .getBannerInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BannerInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(BannerInfo bannerInfo) {
                        Log.e("Banner","onNext");
                        if(bannerInfo.getErrorCode() == 0){
                            loadSuccess(bannerInfo);
                        }else {
                            loadFail(bannerInfo.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Banner","onError");
                        setFirst(false);
                        loadFail(e.getMessage(),new PagingResult(true,true,false));
                    }

                    @Override
                    public void onComplete() {
                        setFirst(false);
                    }
                });
    }

    @Override
    protected boolean isNeedToUpdate() {
        long time = System.currentTimeMillis() - BaseDataPreferenceUtil.getInstance().getLong(mCachedPreferenceKey);
        if(time/(24*3600*1000) > 1) return true;
        return false;
    }

    @Override
    protected Type getTClass() {
        return BannerInfo.class;
    }
}
