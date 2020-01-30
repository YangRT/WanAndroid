package com.example.administrator.wanandroid.project;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.mainpage.ArticleActivity;
import com.example.administrator.wanandroid.base.BaseArticleAdapter;
import com.example.administrator.wanandroid.base.BaseCustomViewModel;
import com.example.administrator.wanandroid.base.MvvmFragment;
import com.example.administrator.wanandroid.databinding.FragmentProjectBinding;

import java.util.ArrayList;

public class ProjectFragment extends MvvmFragment<FragmentProjectBinding,ProjectViewModel, BaseCustomViewModel> {

    private BaseArticleAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        return R.layout.fragment_project;
    }

    @Override
    public ProjectViewModel getViewModel() {
        if(viewModel == null){
            viewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);
        }
        return viewModel;
    }

    @Override
    public void onListItemInserted(ObservableArrayList<BaseCustomViewModel> sender) {
        adapter.setNewData(sender);
    }

    @Override
    protected String getFragmentTag() {
        return "project";
    }

    @Override
    protected void refreshCancel() {
        if(viewDataBinding.mainPageRefreshLayout.isRefreshing()){
            viewDataBinding.mainPageRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void onRetryBtnBack() {

    }
}
