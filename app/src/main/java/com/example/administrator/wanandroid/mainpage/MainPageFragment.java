package com.example.administrator.wanandroid.mainpage;

import android.arch.lifecycle.Observer;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.BaseArticleAdapter;
import com.example.administrator.wanandroid.base.BaseCustomViewModel;
import com.example.administrator.wanandroid.base.MvvmFragment;
import com.example.administrator.wanandroid.databinding.FragmentArticleBinding;



import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

public class MainPageFragment extends MvvmFragment<FragmentArticleBinding, MainPageViewModel, BaseCustomViewModel>{

    private FrameLayout headView;
    private BaseArticleAdapter adapter;
    private BGABanner mBanner;
    private List<String> bannerTitle = new ArrayList<>();
    private List<String> bannerPath = new ArrayList<>();
    private List<String> bannerUrl = new ArrayList<>();


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewDataBinding.articleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        headView = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.banner,null);
        mBanner = headView.findViewById(R.id.banner);
        mBanner.setAdapter(new BGABanner.Adapter<ImageView,String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
                Glide.with(getActivity())
                        .load(model)
                        .placeholder(R.drawable.gift)
                        .error(R.drawable.network)
                        .centerCrop()
                        .dontAnimate()
                        .into(itemView);

            }
        });
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
        adapter.addHeaderView(headView);
        viewDataBinding.articleRecyclerView.setAdapter(adapter);
        viewDataBinding.articleRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        viewModel.bannerData.observe(this, new Observer<BannerInfo>() {
            @Override
            public void onChanged(@Nullable BannerInfo bannerInfo) {
                getBannerData(bannerInfo);
            }
        });
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_article;
    }

    @Override
    public MainPageViewModel getViewModel() {
        if(viewModel == null){
            viewModel = ViewModelProviders.of(this).get(MainPageViewModel.class);
        }
        return viewModel;
    }

    @Override
    protected boolean isRefreshing() {
        return viewDataBinding.mainPageRefreshLayout.isRefreshing();
    }


    @Override
    public void onListItemInserted(ObservableArrayList<BaseCustomViewModel> sender) {
            adapter.setNewData(sender);

    }

   public void getBannerData(BannerInfo data){
        for(BannerInfo.Data item:data.getData()) {
            bannerTitle.add(item.getTitle());
            bannerPath.add(item.getImagePath());
            bannerUrl.add(item.getUrl());
        }
        mBanner.setData(bannerPath,bannerTitle);
        mBanner.setDelegate(new BGABanner.Delegate<ImageView,String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra("url",bannerUrl.get(position));
                intent.putExtra("title",bannerTitle.get(position));
                getActivity().startActivity(intent);
            }
        });
   }

    @Override
    protected void refreshCancel() {
        if(viewDataBinding.mainPageRefreshLayout.isRefreshing()){
            viewDataBinding.mainPageRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected String getFragmentTag() {
        return "article";
    }

    @Override
    protected void onRetryBtnBack() {

    }
}
