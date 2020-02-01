package com.example.administrator.wanandroid.mine.knowledge;

import android.util.Log;

import com.example.administrator.wanandroid.base.BaseArticleInfo;
import com.example.administrator.wanandroid.base.BaseCustomViewModel;
import com.example.administrator.wanandroid.base.MvvmBaseModel;
import com.example.administrator.wanandroid.base.PagingResult;
import com.example.administrator.wanandroid.net.NetUtil;
import com.example.administrator.wanandroid.net.UrlUtil;
import com.example.administrator.wanandroid.utils.BaseDataPreferenceUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class KnowledgeModel extends MvvmBaseModel<List<KnowledgeInfo.Data>> {

    public KnowledgeModel() {
        super(false,"knowledge",null);
    }

    @Override
    public void refresh() {
        load();
    }

    @Override
    protected void load() {
        NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl)
                .create(KnowledgeService.class)
                .getKnowledgeInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<KnowledgeInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(KnowledgeInfo knowledgeInfo) {
                        if(knowledgeInfo.getErrorCode() == 0){
                            List<KnowledgeInfo.Data> list = knowledgeInfo.getData();
                            boolean isEmpty = list.size() == 0;
                            boolean isFirst = pageNum == 1;
                            loadSuccess(list,new PagingResult(isEmpty,true,false));
                        }else {
                            loadFail(knowledgeInfo.getErrorMsg(),new PagingResult(true,true,false));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
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
        return new TypeToken<List<KnowledgeInfo.Data>>() {
        }.getType();
    }

    @Override
    protected boolean isNeedToUpdate() {
        long time = System.currentTimeMillis() - BaseDataPreferenceUtil.getInstance().getLong(mCachedPreferenceKey);
        if(time/(24*3600*1000) > 7) return true;;
        return false;
    }
}
