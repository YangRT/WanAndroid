package com.example.administrator.wanandroid.base;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;

import com.example.administrator.wanandroid.viewstatus.ViewStatus;

import java.util.List;

public abstract class MvvmBaseViewModel<T extends MvvmBaseModel,S> extends ViewModel implements LifecycleObserver, IBaseModelListener<List<S>> {

    protected T model;

    public MutableLiveData<ObservableList<S>> dataList = new MutableLiveData<>();
    public MutableLiveData<ViewStatus> viewStatusLiveData = new MutableLiveData<>();
    public static MutableLiveData<String> errorMsg = new MutableLiveData<>();

    public MvvmBaseViewModel(){
        dataList.setValue(new ObservableArrayList<S>());
        errorMsg.setValue("");
        viewStatusLiveData.setValue(ViewStatus.LOADING);
    }

    public void tryToRefresh(){
        if(model != null){
            model.refresh();
        }
    }

    @Override
    protected void onCleared() {
        if(model != null){
            model.cancel();
        }
    }

    @Override
    public void loadFinish(MvvmBaseModel viewModel, List<S> data, PagingResult... results) {
        Log.e("MvvmBaseViewModel","finish");
        if(viewModel == model){
            if(viewModel.isPaging()){
                if(results[0].isFirst()){
                    dataList.getValue().clear();
                }
                if(results[0].isEmpty()){
                    if(results[0].isFirst()){
                        viewStatusLiveData.setValue(ViewStatus.EMPTY);
                    }else {
                        viewStatusLiveData.setValue(ViewStatus.NO_MORE_DATA);
                    }
                }else {
                   dataList.getValue().addAll(data);
                   dataList.postValue(dataList.getValue());
                    viewStatusLiveData.setValue(ViewStatus.SHOW_CONTENT);
                }
            }else {
                dataList.getValue().clear();
                dataList.getValue().addAll(data);
                dataList.postValue(dataList.getValue());
                viewStatusLiveData.setValue(ViewStatus.SHOW_CONTENT);
            }
        }
    }

    @Override
    public void loadFail(MvvmBaseModel viewModel, String msg, PagingResult... results) {
        Log.e("MvvmBaseViewModel","fail");
        errorMsg.postValue(msg);
        //viewStatusLiveData.postValue(ViewStatus.REFRESH_ERROR);
        if(viewModel.isPaging() && !results[0].isFirst() && dataList.getValue().size()>0){
            viewStatusLiveData.setValue(ViewStatus.LOAD_MORE_FAILED);
        }else if(dataList.getValue().size()>0){
            viewStatusLiveData.setValue(ViewStatus.REFRESH_ERROR);
        } else if(dataList.getValue().size() == 0){
            viewStatusLiveData.setValue(ViewStatus.EMPTY);
        }
    }
}



