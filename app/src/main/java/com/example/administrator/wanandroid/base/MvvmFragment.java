package com.example.administrator.wanandroid.base;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.wanandroid.viewstatus.EmptyCallBack;
import com.example.administrator.wanandroid.viewstatus.LoadingCallback;
import com.example.administrator.wanandroid.viewstatus.NetworkErrorCallback;
import com.example.administrator.wanandroid.viewstatus.ViewStatus;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

// v 层基类  实现页面状态监听，切换及观察数据
public abstract class MvvmFragment<V extends ViewDataBinding,VM extends MvvmBaseViewModel,D> extends Fragment implements Observer {

    protected VM viewModel;
    protected V viewDataBinding;
    protected String mFragmentTag = "";
    protected LoadService mLoadService;

    public abstract int getBindingVariable();

    public abstract @LayoutRes int getLayoutId();

    public abstract VM getViewModel();

    public abstract void onListItemInserted(ObservableArrayList<D> sender);

    protected abstract String getFragmentTag();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(getFragmentTag(),"Activity:"+getActivity()+",Fragment:"+this+"  onCreate");
        initParameters();
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false);
        mLoadService = LoadSir.getDefault().register(viewDataBinding.getRoot(), new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                viewModel.tryToRefresh();
            }
        });
        return mLoadService.getLoadLayout();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(getFragmentTag(),"Activity:"+getActivity()+",Fragment:"+this+"  onViewCreated");
        viewModel = getViewModel();
        getLifecycle().addObserver(viewModel);
        viewModel.viewStatusLiveData.observe(this,this);
        viewModel.dataList.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                    Log.e("MvvmFragment","onChanged");
                    Log.e("MvvmFragment",o.toString());
                    onListItemInserted((ObservableArrayList<D>)o);
            }
        });
        viewModel.errorMsg.observe(this,this);
        if(getBindingVariable() > 0){
            viewDataBinding.setVariable(getBindingVariable(),viewModel);
            viewDataBinding.executePendingBindings();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(getFragmentTag(),"Activity:"+getActivity()+",Fragment:"+this+"  onActivityCreated");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(getFragmentTag(),"Activity:"+getActivity()+",Fragment:"+this+"  onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(getFragmentTag(),"Activity:"+getActivity()+",Fragment:"+this+"  onDetach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(getFragmentTag(),"Activity:"+getActivity()+",Fragment:"+this+"  onDestroyView");
    }

    public void setLoadService(View view){
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onRetryBtnBack();
            }
        });
        mLoadService.showCallback(LoadingCallback.class);
    }

    @Override
    public void onChanged(@Nullable Object o) {
        if(o instanceof ViewStatus && mLoadService != null){
            switch ((ViewStatus) o){
                case LOADING:
                    mLoadService.showCallback(LoadingCallback.class);
                    break;
                case SHOW_CONTENT:
                    if(isRefreshing()){
                        Toast.makeText(getContext(),"刷新成功！",Toast.LENGTH_SHORT).show();
                    }
                    loadMoreFinish();
                    mLoadService.showSuccess();
                    refreshCancel();
                    break;
                case NO_MORE_DATA:
                    loadMoreEmpty();
                    Toast.makeText(getContext(),"没有更多数据！",Toast.LENGTH_SHORT).show();
                    break;
                case LOAD_MORE_FAILED:
                    loadMoreFail();
                    Toast.makeText(getContext(),"加载数据失败,请检查网络！",Toast.LENGTH_SHORT).show();
                    break;
                case EMPTY:
                    mLoadService.showCallback(EmptyCallBack.class);
                    break;
                case REFRESH_ERROR:
                    Toast.makeText(getContext(),"刷新失败,请检查网络！",Toast.LENGTH_SHORT).show();
                    refreshCancel();
                    break;
                case REQUEST_ERROR:
                    mLoadService.showCallback(NetworkErrorCallback.class);
                    Toast.makeText(getContext(),"请求失败,请检查网络！",Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }

    protected boolean isRefreshing(){
        return false;
    }

    protected void loadMoreFail(){};

    protected void loadMoreFinish(){}

    protected void loadMoreEmpty(){};


    protected abstract void refreshCancel();

    protected void showSuccess(){
        if(mLoadService != null){
            mLoadService.showSuccess();
        }


    }

    protected void showLoading(){

    }

    //初始化参数
    protected void initParameters(){}

    protected abstract void onRetryBtnBack();

}
