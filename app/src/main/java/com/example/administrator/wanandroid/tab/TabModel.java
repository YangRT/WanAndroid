package com.example.administrator.wanandroid.tab;

import com.example.administrator.wanandroid.base.BaseCustomViewModel;
import com.example.administrator.wanandroid.base.MvvmBaseModel;
import com.example.administrator.wanandroid.base.PagingResult;
import com.example.administrator.wanandroid.mine.article.MyArticleService;
import com.example.administrator.wanandroid.mine.gzh.GzhListInfo;
import com.example.administrator.wanandroid.net.NetUtil;
import com.example.administrator.wanandroid.net.UrlUtil;
import com.example.administrator.wanandroid.project.classic.ClassicListInfo;
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

public class TabModel extends MvvmBaseModel<List<TabTitleInfo>> {


    public TabModel(String type) {
        super(false,type,null);
    }

    @Override
    public void refresh() {
        load();
    }

    @Override
    protected void load() {
        TabService tabService = NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl)
                .create(TabService.class);
        if(mCachedPreferenceKey.equals("gzh")){
            loadGzhTitles(tabService);
        }else if(mCachedPreferenceKey.equals("classic")){
            loadClassicTitles(tabService);
        }

    }

    private void loadClassicTitles(TabService service){
        service.getClassicListInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ClassicListInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(ClassicListInfo classicListInfo) {
                        if(classicListInfo.getErrorCode() == 0){
                            List<TabTitleInfo> list = new ArrayList<>();
                            for(ClassicListInfo.Data data:classicListInfo.getData()){
                                TabTitleInfo tabTitleInfo = new TabTitleInfo();
                                tabTitleInfo.setTitle(data.getName());
                                tabTitleInfo.setId(data.getId());
                                list.add(tabTitleInfo);
                            }
                            loadSuccess(list);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (e.getMessage() !=null && !e.getMessage().isEmpty()){
                            boolean isFirst = pageNum == 0;
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

    private void loadGzhTitles(TabService tabService){
        tabService.getGzhListInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GzhListInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(GzhListInfo gzhListInfo) {
                        if(gzhListInfo.getErrorCode() == 0){
                            List<TabTitleInfo> list = new ArrayList<>();
                            for(GzhListInfo.Data data:gzhListInfo.getData()){
                                TabTitleInfo tabTitleInfo = new TabTitleInfo();
                                tabTitleInfo.setTitle(data.getName());
                                tabTitleInfo.setId(data.getId());
                                list.add(tabTitleInfo);
                            }
                            loadSuccess(list);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (e.getMessage() !=null && !e.getMessage().isEmpty()){
                            boolean isFirst = pageNum == 0;
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
    protected boolean isNeedToUpdate() {
        long time = System.currentTimeMillis() - BaseDataPreferenceUtil.getInstance().getLong(mCachedPreferenceKey);
        if(time/(24*3600*1000) > 7) return true;;
        return false;
    }

    @Override
    protected Type getTClass() {
        return new TypeToken<List<TabTitleInfo>>() {
        }.getType();
    }
}
