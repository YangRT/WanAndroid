package com.example.administrator.wanandroid.mine.knowledge;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.MvvmFragment;
import com.example.administrator.wanandroid.databinding.FragmentKnowledgeBinding;
import com.example.administrator.wanandroid.mine.ListActivity;
import com.example.administrator.wanandroid.mine.MineAdapter;
import com.example.administrator.wanandroid.mine.MineItemInfo;
import com.example.administrator.wanandroid.mine.MineViewModel;
import com.example.administrator.wanandroid.utils.BaseDataPreferenceUtil;

import java.util.ArrayList;

public class KnowledgeFragment extends MvvmFragment<FragmentKnowledgeBinding,KnowledgeViewModel,KnowledgeInfo.Data> {

    private KnowledgeAdapter knowledgeAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewDataBinding.knowledgeRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        viewDataBinding.knowledgeRecyclerview.addItemDecoration(new SpaceItemDecoration(50));
        knowledgeAdapter = new KnowledgeAdapter(getContext(),R.layout.knowledge_tree_item,new ArrayList<KnowledgeInfo.Data>());
        viewDataBinding.knowledgeRecyclerview.setAdapter(knowledgeAdapter);
        knowledgeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_knowledge;
    }

    @Override
    public KnowledgeViewModel getViewModel() {
        if(viewModel == null){
            viewModel = ViewModelProviders.of(this).get(KnowledgeViewModel.class);
        }
        return viewModel;
    }

    @Override
    public void onListItemInserted(ObservableArrayList<KnowledgeInfo.Data> sender) {
        knowledgeAdapter.setNewData(sender);
    }

    @Override
    protected String getFragmentTag() {
        return "knowledge";
    }

    @Override
    protected void refreshCancel() {

    }

    @Override
    protected void onRetryBtnBack() {

    }
}
