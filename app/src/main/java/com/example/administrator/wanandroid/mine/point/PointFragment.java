package com.example.administrator.wanandroid.mine.point;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.MvvmFragment;
import com.example.administrator.wanandroid.databinding.FragmentListBinding;
import com.example.administrator.wanandroid.mine.rank.RankAdapter;
import com.example.administrator.wanandroid.mine.rank.RankInfo;
import com.example.administrator.wanandroid.mine.rank.RankViewModel;

import java.util.ArrayList;

public class PointFragment extends MvvmFragment<FragmentListBinding,PointViewModel,PointInfo.Datas> {

    private PointAdapter pointAdapter;
    private FrameLayout headView;
    private String count;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getArguments() != null){
            count = getArguments().getString("count");
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        headView = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.point_headview,null);
        TextView tvCount = headView.findViewById(R.id.point_total);
        if(count != null){
            tvCount.setText(count);
        }
        viewDataBinding.articleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pointAdapter = new PointAdapter(R.layout.point_item,new ArrayList<PointInfo.Datas>());
        pointAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
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
        pointAdapter.addHeaderView(headView);
        viewDataBinding.articleRecyclerView.setAdapter(pointAdapter);
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
    public PointViewModel getViewModel() {
        if(viewModel == null){
            viewModel = ViewModelProviders.of(this).get(PointViewModel.class);
        }
        return viewModel;
    }

    @Override
    public void onListItemInserted(ObservableArrayList<PointInfo.Datas> sender) {
        pointAdapter.setNewData(sender);
    }

    @Override
    protected String getFragmentTag() {
        return "point";
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
