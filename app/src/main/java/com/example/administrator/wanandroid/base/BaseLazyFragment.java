package com.example.administrator.wanandroid.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//实现懒加载
public abstract class BaseLazyFragment<V extends ViewDataBinding,VM extends MvvmBaseViewModel,D> extends MvvmFragment<V,VM,D>{

    public boolean isFragmentVisible = false;
    public boolean viewIsCreated = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewIsCreated = true;
        initView();
        //setUserVisibleHint调用时期比fragment早，所以viewIsCreated为false
        //当onCreatedView执行后，如果getUserVisibleHint()为true即Fragment可见
        //需再次调用来分发状态
        if(getUserVisibleHint()){
            setUserVisibleHint(true);
        }
    }

    protected abstract void dispatchUserVisible(boolean visible);

    protected abstract void initView();


    public  void onFragmentLoading(){}

    public void onFragmentLoadStop(){}

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(viewIsCreated){
            if(isVisibleToUser && !isFragmentVisible){
                dispatchUserVisible(true);
            }else if(!isVisibleToUser && isFragmentVisible){
                dispatchUserVisible(false);
            }
        }
    }
}
