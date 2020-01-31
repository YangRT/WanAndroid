package com.example.administrator.wanandroid.tab;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.MvvmFragment;
import com.example.administrator.wanandroid.databinding.FragmentTabBinding;
import com.example.administrator.wanandroid.mine.article.MyArticleFragment;
import com.example.administrator.wanandroid.mine.article.MyArticleViewModel;
import com.example.administrator.wanandroid.mine.gzh.GzhFragment;

import java.util.ArrayList;
import java.util.List;

public class TabFragment extends MvvmFragment<FragmentTabBinding,TabViewModel,TabTitleInfo> {

    List<String> title = new ArrayList<>();
    List<Integer> id = new ArrayList<>();
    List<Fragment> mFragments = new ArrayList<>();
    private String type;
    private TabFragmentStatePagerAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getArguments() != null){
            type = getArguments().getString("type");
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new TabFragmentStatePagerAdapter(getChildFragmentManager(),mFragments,title);

    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tab;
    }

    @Override
    public TabViewModel getViewModel() {
        if(viewModel == null){
            TabFragment.MyViewModelFactory factory = new TabFragment.MyViewModelFactory(type);
            viewModel = factory.create(TabViewModel.class);
        }
        return viewModel;
    }

    @Override
    public void onListItemInserted(ObservableArrayList<TabTitleInfo> sender) {
        title.clear();
        id.clear();
        mFragments.clear();
        for(TabTitleInfo info:sender){
            title.add(info.getTitle());
            id.add(info.getId());
        }
        createFragment();
    }

    private void createFragment() {
        if(type.equals("gzh")){
            addGzhFragment();
        }
        viewDataBinding.tabViewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        viewDataBinding.tabLayout.setupWithViewPager(viewDataBinding.tabViewPager);
    }

    private void addGzhFragment() {
        for (int i = 0;i < title.size();i++){
            Fragment fragment = new GzhFragment();
            Bundle bundle = new Bundle();
            bundle.putString("key",title.get(i));
            bundle.putInt("id",id.get(i));
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }
    }

    @Override
    protected String getFragmentTag() {
        return null;
    }

    @Override
    protected void refreshCancel() {

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
            return (T)new TabViewModel(key);
        }
    }
}
