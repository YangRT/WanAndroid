package com.example.administrator.wanandroid.mine.gzh;

import android.util.Log;

import com.example.administrator.wanandroid.base.BaseArticleInfo;
import com.example.administrator.wanandroid.base.BaseCustomViewModel;
import com.example.administrator.wanandroid.base.MvvmBaseModel;
import com.example.administrator.wanandroid.base.MvvmBaseViewModel;
import com.example.administrator.wanandroid.base.PagingResult;
import com.example.administrator.wanandroid.net.NetUtil;
import com.example.administrator.wanandroid.net.UrlUtil;
import com.example.administrator.wanandroid.utils.BaseDataPreferenceUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GzhModel extends MvvmBaseModel<List<BaseCustomViewModel>> {

    private int id;

    public GzhModel(String key,int id) {
        super(true,key,null);
        this.id = id;
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
                .create(GzhService.class)
                .getGzhInfo(id,pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseArticleInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseArticleInfo baseArticleInfo) {
                        if(baseArticleInfo.getErrorCode() == 0){
                            pageNum = isRefreshing ? 2 : pageNum+1;
                            ArrayList<BaseCustomViewModel> list = new ArrayList<>();
                            for(BaseArticleInfo.DataBean.DatasBean datasBean: baseArticleInfo.getData().getDatas()){
                                BaseCustomViewModel model = new BaseCustomViewModel(BaseCustomViewModel.NORMAL);
                                model.setJumpUrl(datasBean.getLink());
                                if(datasBean.getAuthor().equals("")){
                                    model.setAuthor(datasBean.getShareUser());
                                }else {
                                    model.setAuthor(datasBean.getAuthor());
                                }
                                model.setCollect(datasBean.getCollect());
                                model.setTime(datasBean.getNiceDate());
                                model.setTitle(datasBean.getTitle());
                                model.setId(datasBean.getId());
                                model.setClassic(datasBean.getSuperChapterName()+"/"+datasBean.getChapterName());
                                list.add(model);
                            }
                            boolean isEmpty = list.size() == 0;
                            boolean isFirst = pageNum == 2;
                            boolean hasNextPage = baseArticleInfo.getData().getCurPage() != baseArticleInfo.getData().getPageCount();
                            loadSuccess(list,new PagingResult(isEmpty,isFirst,hasNextPage));
                        }else {
                            boolean isFirst = pageNum == 1;
                            boolean hasNextPage = baseArticleInfo.getData().getCurPage() != baseArticleInfo.getData().getPageCount();
                            loadFail(baseArticleInfo.getErrorMsg(),new PagingResult(true,isFirst,hasNextPage));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
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
        return new TypeToken<List<BaseCustomViewModel>>() {
        }.getType();
    }

}
