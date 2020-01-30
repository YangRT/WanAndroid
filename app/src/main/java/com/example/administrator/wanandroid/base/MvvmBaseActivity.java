package com.example.administrator.wanandroid.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kingja.loadsir.core.LoadService;

public abstract class MvvmBaseActivity<V extends ViewDataBinding,VM extends MvvmBaseViewModel> extends AppCompatActivity {

    protected VM viewModel;
    protected V viewDataBinding;
    private LoadService loadService;

    public abstract @LayoutRes int getLayoutId();

    protected abstract VM getViewModel();

    public abstract int getBindingVariable();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        preformDataBinding();
        if(viewModel != null){
            getLifecycle().addObserver(viewModel);
        }

    }

    private void preformDataBinding(){
        viewDataBinding = DataBindingUtil.setContentView(this,getLayoutId());
        if(viewModel == null){
            this.viewModel = getViewModel();
        }
        if(getBindingVariable() > 0){
            viewDataBinding.setVariable(getBindingVariable(),viewModel);
        }
        viewDataBinding.executePendingBindings();
    }

    private void initViewModel(){
        viewModel = getViewModel();
    }

}
