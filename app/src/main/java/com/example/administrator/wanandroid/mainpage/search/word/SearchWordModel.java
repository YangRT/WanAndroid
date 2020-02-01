package com.example.administrator.wanandroid.mainpage.search.word;

import com.example.administrator.wanandroid.base.BaseArticleInfo;
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
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchWordModel extends MvvmBaseModel<List<SearchWordInfo.Data>> {

    public SearchWordModel() {
        super(false,"SearchWord", null);
    }

    @Override
    public void refresh() {
        load();
    }

    @Override
    protected void load() {
        NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl)
                .create(SearchWordService.class)
                .getSearchWordInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchWordInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(SearchWordInfo searchWordInfo) {
                        if(searchWordInfo.getErrorCode() == 0){
                            List<SearchWordInfo.Data> list = searchWordInfo.getData();
                            boolean isEmpty = list.size() == 0;
                            loadSuccess(list,new PagingResult(isEmpty,true,false));

                        }else {
                            loadFail(searchWordInfo.getErrorMsg(),new PagingResult(true,true,false));
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
    protected boolean isNeedToUpdate() {
        long time = System.currentTimeMillis() - BaseDataPreferenceUtil.getInstance().getLong(mCachedPreferenceKey);
        if(time/(24*3600*1000) > 5) return true;;
        return false;
    }

    @Override
    protected Type getTClass() {
        return new TypeToken<List<SearchWordInfo.Data>>() {
        }.getType();
    }
}
