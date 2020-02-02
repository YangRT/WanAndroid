package com.example.administrator.wanandroid.mainpage.search.article;

import android.util.Log;

import com.example.administrator.wanandroid.base.BaseArticleInfo;
import com.example.administrator.wanandroid.base.BaseCustomViewModel;
import com.example.administrator.wanandroid.base.MvvmBaseModel;
import com.example.administrator.wanandroid.base.PagingResult;
import com.example.administrator.wanandroid.net.NetUtil;
import com.example.administrator.wanandroid.net.UrlUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchArticleModel extends MvvmBaseModel<List<BaseCustomViewModel>> {

    private String key;

    public SearchArticleModel(String key) {
        super(true,null,null);
        this.key = key;
    }

    @Override
    public void refresh() {
        isRefreshing = true;
        pageNum = 0;
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
                .create(SearchArticleService.class)
                .getSearchArticleInfo(pageNum,key)
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
                            pageNum = isRefreshing ? 1 : pageNum+1;
                            ArrayList<BaseCustomViewModel> list = new ArrayList<>();
                            for(BaseArticleInfo.DataBean.DatasBean datasBean: baseArticleInfo.getData().getDatas()){
                                BaseCustomViewModel model = null;
                                if(datasBean.getEnvelopePic() != null && datasBean.getEnvelopePic().length()>0){
                                    model = new BaseCustomViewModel(BaseCustomViewModel.PROJECT);
                                    model.setPath(datasBean.getEnvelopePic());
                                    model.setDescription(datasBean.getDesc());
                                }else {
                                    model = new BaseCustomViewModel(BaseCustomViewModel.NORMAL);
                                }
                                model.setJumpUrl(datasBean.getLink());
                                if(datasBean.getAuthor().equals("")){
                                    model.setAuthor(datasBean.getShareUser());
                                }else {
                                    model.setAuthor(datasBean.getAuthor());
                                }
                                model.setCollect(datasBean.getCollect());
                                model.setTime(datasBean.getNiceDate());
                                model.setId(datasBean.getId());
                                String title = datasBean.getTitle().replaceAll("<em class='highlight'>","");
                                title = title.replace("</em>","");
                                title = title.replace("&amp;","");
                                title = title.replace("&mdash;","");
                                model.setTitle(title);
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
                        if (e.getMessage() !=null && !e.getMessage().isEmpty()){
                            boolean isFirst = pageNum == 0;
                            Log.e("onError:load","isFirst:"+isFirst);
                            loadFail(e.getMessage(),new PagingResult(true,isFirst,true));
                        }
                        setFirst(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
