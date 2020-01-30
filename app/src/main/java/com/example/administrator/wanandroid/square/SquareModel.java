package com.example.administrator.wanandroid.square;

import android.util.Log;

import com.example.administrator.wanandroid.base.BaseArticleInfo;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SquareModel extends MvvmBaseModel<List<BaseCustomViewModel>> {

    public SquareModel() {
        super(true,"Square",null);
    }

    @Override
    public void refresh() {
        isRefreshing = true;
        pageNum = 0;
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
                .create(SquareService.class)
                .getSquareArticleInfo(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseArticleInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseArticleInfo baseArticleInfo) {
                        Log.e("onNext:load", baseArticleInfo.getErrorCode()+"");
                        if(baseArticleInfo.getErrorCode() == 0){
                            pageNum = isRefreshing ? 1 : pageNum+1;
                            ArrayList<BaseCustomViewModel> list = new ArrayList<>();
                            for(BaseArticleInfo.DataBean.DatasBean datasBean: baseArticleInfo.getData().getDatas()){
                                BaseCustomViewModel model = new BaseCustomViewModel(BaseCustomViewModel.NORMAL);
                                model.setJumpUrl(datasBean.getLink());
                                model.setAuthor(datasBean.getShareUser());
                                model.setCollect(datasBean.getCollect());
                                model.setTime(datasBean.getNiceDate());
                                model.setTitle(datasBean.getTitle());
                                model.setClassic(datasBean.getSuperChapterName()+"/"+datasBean.getChapterName());
                                list.add(model);
                            }
                            boolean isEmpty = list.size() == 0;
                            boolean isFirst = pageNum == 1;
                            boolean hasNextPage = baseArticleInfo.getData().getCurPage() != baseArticleInfo.getData().getPageCount();
                            loadSuccess(list,new PagingResult(isEmpty,isFirst,hasNextPage));
                        }else {
                            boolean isFirst = pageNum == 0;
                            boolean hasNextPage = baseArticleInfo.getData().getCurPage() != baseArticleInfo.getData().getPageCount();
                            loadFail(baseArticleInfo.getErrorMsg(),new PagingResult(true,isFirst,hasNextPage));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError:load",e.getMessage());
                        if (e.getMessage() !=null && !e.getMessage().isEmpty()){
                            boolean isFirst = pageNum == 0;
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
        return new TypeToken<List<BaseCustomViewModel>>() {
        }.getType();
    }
}