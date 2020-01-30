package com.example.administrator.wanandroid.mine.rank;

import android.util.Log;

import com.example.administrator.wanandroid.base.BaseCustomViewModel;
import com.example.administrator.wanandroid.base.MvvmBaseModel;
import com.example.administrator.wanandroid.base.PagingResult;
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

public class RankModel extends MvvmBaseModel<List<RankInfo.Datas>> {

    public RankModel() {
        super(true,"rank",null);
        pageNum = 1;
    }

    @Override
    public void refresh() {
        isRefreshing = true;
        pageNum = 1;
        load();
    }

    public void loadNextPage(){
        isRefreshing = false;
        Log.e("LoadNextPage","load:"+pageNum);
        load();
    }

    @Override
    protected void load() {
        NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl)
                .create(RankService.class)
                .getRankInfo(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RankInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(RankInfo rankInfo) {
                        if(rankInfo.getErrorCode() == 0){
                            pageNum = isRefreshing ? 1 : pageNum+1;
                            List<RankInfo.Datas> list;
                            list = rankInfo.getData().getDatas();
                            boolean isEmpty = list.size() == 0;
                            boolean isFirst = pageNum == 2;
                            boolean hasNextPage = rankInfo.getData().getCurPage() != rankInfo.getData().getPageCount();
                            loadSuccess(list,new PagingResult(isEmpty,isFirst,hasNextPage));
                        }else {
                            boolean isFirst = pageNum == 0;
                            boolean hasNextPage = rankInfo.getData().getCurPage() != rankInfo.getData().getPageCount();
                            loadFail(rankInfo.getErrorMsg(),new PagingResult(true,isFirst,hasNextPage));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage() !=null && !e.getMessage().isEmpty()){
                            boolean isFirst = pageNum == 1;
                            loadFail(e.getMessage(),new PagingResult(true,isFirst,true));
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected Type getTClass() {
        return new TypeToken<List<RankInfo.Datas>>() {
        }.getType();
    }
}
