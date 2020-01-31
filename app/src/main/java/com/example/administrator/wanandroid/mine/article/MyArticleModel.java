package com.example.administrator.wanandroid.mine.article;

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

public class MyArticleModel extends MvvmBaseModel<List<BaseCustomViewModel>> {

    private boolean isFirst = true;

    public MyArticleModel(String key) {
        super(true, key,null);
        if(key.equals("share")){
            pageNum = 1;
        }
    }

    @Override
    public void refresh() {
        isRefreshing = true;
        if(mCachedPreferenceKey.equals("collect")){
            pageNum = 0;
        }else {
            pageNum = 1;
        }
        load();
    }

    public void loadNextPage(){
        if(isFirst){
            return;
        }
        isRefreshing = false;
        Log.e("LoadNextPage","load:"+pageNum);
        load();
    }

    @Override
    protected synchronized void load() {
        Log.e("MyArticleModel","load-"+pageNum);
        MyArticleService service = NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl)
                .create(MyArticleService.class);
        if(mCachedPreferenceKey.equals("collect")){
            service.getCollectInfo(pageNum)
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
                                for(BaseArticleInfo.DataBean.DatasBean dataBean : baseArticleInfo.getData().getDatas()){
                                    BaseCustomViewModel model = null;
                                    if(dataBean.getEnvelopePic() != null && dataBean.getEnvelopePic().length()>0){
                                        model = new BaseCustomViewModel(BaseCustomViewModel.PROJECT);
                                        model.setPath(dataBean.getEnvelopePic());
                                        model.setDescription(dataBean.getDesc());
                                    }else {
                                        model = new BaseCustomViewModel(BaseCustomViewModel.NORMAL);
                                    }
                                    model.setAuthor(dataBean.getAuthor());
                                    model.setCollect(true);
                                    model.setTime(dataBean.getNiceDate());
                                    model.setTitle(dataBean.getTitle());
                                    model.setClassic(dataBean.getChapterName());
                                    list.add(model);
                                }
                                boolean isEmpty = list.size() == 0;
                                boolean isFirst = pageNum == 1;
                                boolean hasNextPage = baseArticleInfo.getData().getCurPage() != baseArticleInfo.getData().getPageCount();
                                loadSuccess(list,new PagingResult(isEmpty,isFirst,hasNextPage));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (e.getMessage() !=null && !e.getMessage().isEmpty()){
                                boolean isFirst = pageNum == 0;
                                loadFail(e.getMessage(),new PagingResult(true,isFirst,true));
                            }
                        }

                        @Override
                        public void onComplete() {
                            isFirst =false;
                        }
                    });
        }else {
            service.getShareInfo(pageNum)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ShareInfo>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onNext(ShareInfo shareInfo) {
                            if(shareInfo.getErrorCode() == 0){
                                pageNum = isRefreshing ? 2 : pageNum+1;
                                ArrayList<BaseCustomViewModel> list = new ArrayList<>();
                                for(BaseArticleInfo.DataBean.DatasBean datasBean:shareInfo.getData().getShareArticles().getDatas()){
                                    BaseCustomViewModel model = null;
                                    if(datasBean.getEnvelopePic() != null && datasBean.getEnvelopePic().length()>0){
                                        model = new BaseCustomViewModel(BaseCustomViewModel.WITH_PIC);
                                        model.setPath(datasBean.getEnvelopePic());
                                    }else {
                                        model = new BaseCustomViewModel(BaseCustomViewModel.NORMAL);
                                    }
                                    model.setAuthor(datasBean.getShareUser());
                                    model.setCollect(datasBean.getCollect());
                                    model.setTime(datasBean.getNiceDate());
                                    model.setTitle(datasBean.getTitle());
                                    model.setClassic(datasBean.getSuperChapterName()+"/"+datasBean.getChapterName());
                                    list.add(model);
                                }
                                Log.e("MyArticleModel",list.size()+"");
                                Log.e("MyArticleModel","success-"+pageNum);
                                boolean isEmpty = list.size() == 0;
                                boolean isFirst = pageNum == 2;
                                boolean hasNextPage = shareInfo.getData().getShareArticles().getCurPage() != shareInfo.getData().getShareArticles().getPageCount();
                                loadSuccess(list,new PagingResult(isEmpty,isFirst,hasNextPage));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (e.getMessage() !=null && !e.getMessage().isEmpty()){
                                boolean isFirst = pageNum == 1;
                                loadFail(e.getMessage(),new PagingResult(true,isFirst,true));
                            }
                            isFirst = false;
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {
                            Log.e("MyArticle","finish");
                            isFirst = false;
                        }
                    });
        }
    }

    @Override
    protected Type getTClass() {
        return new TypeToken<List<BaseCustomViewModel>>() {
        }.getType();
    }
}
