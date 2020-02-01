package com.example.administrator.wanandroid.mine.gzh;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.BaseArticleAdapter;
import com.example.administrator.wanandroid.base.BaseCustomViewModel;
import com.example.administrator.wanandroid.base.BaseLazyFragment;
import com.example.administrator.wanandroid.base.MvvmBaseViewModel;
import com.example.administrator.wanandroid.databinding.FragmentListBinding;
import com.example.administrator.wanandroid.mainpage.ArticleActivity;
import com.example.administrator.wanandroid.tab.TabFragment;
import com.example.administrator.wanandroid.tab.TabViewModel;

import java.util.ArrayList;

public class GzhFragment extends BaseLazyFragment<FragmentListBinding,GzhViewModel, BaseCustomViewModel> {

    private String key;
    private BaseArticleAdapter adapter;
    private int id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getArguments() != null){
            key = getArguments().getString("key");
            id = getArguments().getInt("id");
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void dispatchUserVisible(boolean visible) {
        isFragmentVisible = visible;
        if(visible){
            onFragmentLoading();
        }else {
            onFragmentLoadStop();
        }
    }

    @Override
    protected void initView() {
        viewDataBinding.articleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BaseArticleAdapter(getContext(),new ArrayList<BaseCustomViewModel>());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.e("ItemClick","test");
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra("url",((BaseCustomViewModel)adapter.getData().get(position)).getJumpUrl());
                intent.putExtra("title",((BaseCustomViewModel)adapter.getData().get(position)).getTitle());
                getActivity().startActivity(intent);
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getViewModel().tryToLoadNextPage();
            }
        },viewDataBinding.articleRecyclerView);
        viewDataBinding.mainPageRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getViewModel().tryToRefresh();
            }
        });
        viewDataBinding.articleRecyclerView.setAdapter(adapter);
        viewDataBinding.articleRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }



    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    public GzhViewModel getViewModel() {
        if(viewModel == null){
            GzhFragment.MyViewModelFactory factory = new GzhFragment.MyViewModelFactory(key,id);
            viewModel = factory.create(GzhViewModel.class);
        }
        return viewModel;
    }

    @Override
    public void onListItemInserted(ObservableArrayList sender) {
        adapter.setNewData(sender);
    }

    @Override
    protected String getFragmentTag() {
        return key;
    }

    @Override
    protected void loadMoreFinish() {
        if(adapter.isLoading()){
            adapter.loadMoreComplete();
        }
    }

    @Override
    protected void loadMoreEmpty() {
        if(adapter.isLoading()){
            adapter.loadMoreEnd();
        }
    }

    @Override
    protected void loadMoreFail() {
        if(adapter.isLoading()){
            adapter.loadMoreFail();
        }
    }

    @Override
    protected void refreshCancel() {
        if(viewDataBinding.mainPageRefreshLayout.isRefreshing()){
            viewDataBinding.mainPageRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected boolean isRefreshing() {
        return viewDataBinding.mainPageRefreshLayout.isRefreshing();
    }

    @Override
    protected void onRetryBtnBack() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isFragmentVisible && getUserVisibleHint()){
            dispatchUserVisible(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(isFragmentVisible && getUserVisibleHint()){
            dispatchUserVisible(false);
        }
    }

    @Override
    public void onFragmentLoading() {
        if(viewModel != null){
            viewModel.beginLoading();
        }
    }

    @Override
    public void onFragmentLoadStop() {
        refreshCancel();
    }

    class MyViewModelFactory implements ViewModelProvider.Factory{

        private String key;
        private int id;

        MyViewModelFactory(String key,int id){
            this.key = key;
            this.id = id;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T)new GzhViewModel(key,id);
        }
    }
}
