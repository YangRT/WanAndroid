package com.example.administrator.wanandroid.mainpage.search.article;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.BaseArticleAdapter;
import com.example.administrator.wanandroid.base.BaseCustomViewModel;
import com.example.administrator.wanandroid.base.MvvmFragment;
import com.example.administrator.wanandroid.collect.CollectHelper;
import com.example.administrator.wanandroid.databinding.FragmentListBinding;
import com.example.administrator.wanandroid.ArticleActivity;

import java.util.ArrayList;

public class SearchArticleFragment extends MvvmFragment<FragmentListBinding,SearchArticleViewModel, BaseCustomViewModel> implements CollectHelper.CollectCallBackListener{

    private BaseArticleAdapter adapter;
    private String key;
    private CollectHelper helper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getArguments() != null){
            key = getArguments().getString("key");
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        helper = new CollectHelper();
        helper.setCollectCallBackListener(this);
        viewDataBinding.articleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BaseArticleAdapter(getContext(),new ArrayList<BaseCustomViewModel>());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.e("ItemClick","test");
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra("url",((BaseCustomViewModel)adapter.getData().get(position)).getJumpUrl());
                intent.putExtra("title",((BaseCustomViewModel)adapter.getData().get(position)).getTitle());
                intent.putExtra("id",((BaseCustomViewModel)adapter.getData().get(position)).getId());
                intent.putExtra("collect",((BaseCustomViewModel)adapter.getData().get(position)).isCollect());
                getActivity().startActivity(intent);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                BaseCustomViewModel model = (BaseCustomViewModel)adapter.getData().get(position);
                if(model.isCollect()){
                    helper.unCollectArticle(model.getId(),position);
                }else {
                    helper.addCollectArticle(model.getId(),position);
                }
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
    public SearchArticleViewModel getViewModel() {
        if(viewModel == null){
            SearchArticleFragment.MyViewModelFactory factory = new SearchArticleFragment.MyViewModelFactory(key);
            viewModel = factory.create(SearchArticleViewModel.class);
        }
        return viewModel;
    }

    @Override
    public void onListItemInserted(ObservableArrayList<BaseCustomViewModel> sender) {
        adapter.setNewData(sender);
    }

    @Override
    protected String getFragmentTag() {
        return "search";
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
    public void onCollectSuccess(int position) {
        BaseCustomViewModel model = adapter.getData().get(position);
        model.setCollect(true);
        Toast.makeText(getContext(),"收藏成功！",Toast.LENGTH_SHORT).show();
        adapter.setData(position,model);
    }

    @Override
    public void onUnCollectSuccess(int position) {
        BaseCustomViewModel model = adapter.getData().get(position);
        model.setCollect(false);
        Toast.makeText(getContext(),"取消收藏成功！",Toast.LENGTH_SHORT).show();
        adapter.setData(position,model);
    }

    @Override
    public void onFail(String message,int type) {
        if(type == 0){
            Toast.makeText(getContext(),"收藏失败！"+message,Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(),"取消收藏失败！"+message,Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onRetryBtnBack() {

    }

    class MyViewModelFactory implements ViewModelProvider.Factory{

        private String key;

        MyViewModelFactory(String key){
            this.key = key;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T)new SearchArticleViewModel(key);
        }
    }
}
