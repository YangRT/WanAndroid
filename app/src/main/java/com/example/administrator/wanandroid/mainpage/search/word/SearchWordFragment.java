package com.example.administrator.wanandroid.mainpage.search.word;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.MvvmFragment;
import com.example.administrator.wanandroid.databinding.FragmentSearchWordBinding;
import com.example.administrator.wanandroid.mine.knowledge.KnowledgeViewModel;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class SearchWordFragment extends MvvmFragment<FragmentSearchWordBinding,SearchWordViewModel,SearchWordInfo.Data> {


    private SearchWordAdapter adapter;
    private List<SearchWordInfo.Data> list = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new SearchWordAdapter(getContext(),list);
        viewDataBinding.searchWordRecycler.setLayoutManager(new FlexboxLayoutManager(getContext()));
        viewDataBinding.searchWordRecycler.setAdapter(adapter);
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_word;
    }

    @Override
    public SearchWordViewModel getViewModel() {
        if(viewModel == null){
            viewModel = ViewModelProviders.of(this).get(SearchWordViewModel.class);
        }
        return viewModel;
    }

    @Override
    public void onListItemInserted(ObservableArrayList<SearchWordInfo.Data> sender) {
        list.clear();
        list.addAll(sender);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected String getFragmentTag() {
        return "searchWord";
    }

    @Override
    protected void refreshCancel() {

    }

    @Override
    protected void onRetryBtnBack() {

    }
}
