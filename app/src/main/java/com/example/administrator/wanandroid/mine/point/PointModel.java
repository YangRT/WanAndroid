package com.example.administrator.wanandroid.mine.point;

import android.util.Log;

import com.example.administrator.wanandroid.base.MvvmBaseModel;
import com.example.administrator.wanandroid.base.PagingResult;
import com.example.administrator.wanandroid.mine.rank.RankInfo;
import com.example.administrator.wanandroid.net.NetUtil;
import com.example.administrator.wanandroid.net.UrlUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PointModel extends MvvmBaseModel<List<PointInfo.Datas>> {

    public PointModel() {
        super(true,"point",null);
        pageNum = 1;
    }

    @Override
    public void refresh() {
        isRefreshing = true;
        pageNum = 1;
        load();
    }

    public void loadNextPage(){
        if(isFirst()){
            return;
        }
        isRefreshing = false;
        Log.e("LoadNextPage","load:"+pageNum);
        load();
    }

    @Override
    protected void load() {
        NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl)
                .create(PointService.class)
                .getPointInfo(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PointInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(PointInfo pointInfo) {
                        if(pointInfo.getErrorCode() == 0){
                            pageNum = isRefreshing ? 1 : pageNum+1;
                            List<PointInfo.Datas> list;
                            list = pointInfo.getData().getDatas();
                            boolean isEmpty = list.size() == 0;
                            boolean isFirst = pageNum == 2;
                            boolean hasNextPage = pointInfo.getData().getCurPage() != pointInfo.getData().getPageCount();
                            loadSuccess(list,new PagingResult(isEmpty,isFirst,hasNextPage));
                        }else {
                            boolean isFirst = pageNum == 0;
                            boolean hasNextPage = pointInfo.getData().getCurPage() != pointInfo.getData().getPageCount();
                            loadFail(pointInfo.getErrorMsg(),new PagingResult(true,isFirst,hasNextPage));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage() !=null && !e.getMessage().isEmpty()){
                            boolean isFirst = pageNum == 1;
                            loadFail(e.getMessage(),new PagingResult(true,isFirst,true));
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
        return new TypeToken<List<PointInfo.Datas>>() {
        }.getType();
    }
}
