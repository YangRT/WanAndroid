package com.example.administrator.wanandroid.mine.todo;

import android.util.Log;

import com.example.administrator.wanandroid.base.MvvmBaseModel;
import com.example.administrator.wanandroid.base.PagingResult;
import com.example.administrator.wanandroid.net.NetUtil;
import com.example.administrator.wanandroid.net.UrlUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TodoModel extends MvvmBaseModel<List<TodoInfo.Datas>> {

    private Map<String,Integer> map = new HashMap<>();
    private int type = -1;

    public TodoModel(String status) {
        super(true,status, null);
        if(status.equals("finish")){
            map.put("status",1);
        }else {
            map.put("status",0);
        }
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
        load();
    }

    public void getTypeInfo(int type){
        if(this.type == type){
            return;
        }
        this.type = type;
        pageNum = 1;
        if(type > 0){
            map.put("type",type);
        }else{
            map.remove("type");
        }
        load();
    }

    @Override
    protected void load() {
        Log.e("Todo","load");
        NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl)
                .create(TodoService.class)
                .getTodoInfo(pageNum,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TodoInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(TodoInfo todoInfo) {
                        if(todoInfo.getErrorCode() == 0){
                            Log.e("Todo","success");
                            pageNum = isRefreshing ? 2 : pageNum+1;
                            List<TodoInfo.Datas> list = todoInfo.getData().getDatas();
                            Log.e("Todo","length:"+list.size());
                            boolean isEmpty = list.size() == 0;
                            boolean isFirst = pageNum == 2;
                            boolean hasNextPage = todoInfo.getData().getCurPage() != todoInfo.getData().getPageCount();
                            loadSuccess(list,new PagingResult(isEmpty,isFirst,hasNextPage));
                        }else {
                            boolean isFirst = pageNum == 1;
                            boolean hasNextPage = todoInfo.getData().getCurPage() != todoInfo.getData().getPageCount();
                            loadFail(todoInfo.getErrorMsg(),new PagingResult(true,isFirst,hasNextPage));
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
        return new TypeToken<List<TodoInfo.Datas>>() {
        }.getType();
    }
}
