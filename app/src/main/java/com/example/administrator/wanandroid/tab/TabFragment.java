package com.example.administrator.wanandroid.tab;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.MvvmFragment;
import com.example.administrator.wanandroid.databinding.FragmentTabBinding;
import com.example.administrator.wanandroid.mine.article.MyArticleFragment;
import com.example.administrator.wanandroid.mine.article.MyArticleViewModel;
import com.example.administrator.wanandroid.mine.gzh.GzhFragment;
import com.example.administrator.wanandroid.mine.knowledgeitem.KIFragment;
import com.example.administrator.wanandroid.project.classic.ClassicFragment;
import com.example.administrator.wanandroid.viewstatus.ViewStatus;

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
        if(!type.equals("gzh") && !type.equals("classic")){
            title.clear();
            id.clear();
            title.addAll(getArguments().getStringArrayList("tabTitle"));
            id.addAll(getArguments().getIntegerArrayList("tabId"));
            if(title.size() > 0 && id.size() > 0){
                mFragments.clear();
                addKIFragment();
                Log.e("tab","run");
                for (int i = 0;i < title.size();i++){
                    Log.e("tab",title.get(i)+" "+id.get(i));
                }
            }else {
                Toast.makeText(getContext(),"数据为空！",Toast.LENGTH_SHORT).show();
            }

        }
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
        if(type.equals("gzh") || type.equals("classic")){
            title.clear();
            id.clear();
            mFragments.clear();
            for(TabTitleInfo info:sender){
                title.add(info.getTitle());
                id.add(info.getId());
            }
            createFragment();
        }

    }

    private void createFragment() {
        if(type.equals("gzh")){
            addGzhFragment();
        }else if(type.equals("classic")){
            addClassicFragment();
        }
        viewDataBinding.tabViewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        viewDataBinding.tabLayout.setupWithViewPager(viewDataBinding.tabViewPager);
    }

    private void addClassicFragment(){
        for (int i = 0;i < title.size();i++){
            Fragment fragment = new ClassicFragment();
            Bundle bundle = new Bundle();
            bundle.putString("key",title.get(i));
            bundle.putInt("id",id.get(i));
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }
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

    private void addKIFragment(){
        for (int i = 0;i < title.size();i++){
            Fragment fragment = new KIFragment();
            Bundle bundle = new Bundle();
            bundle.putString("key",title.get(i));
            bundle.putInt("id",id.get(i));
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }
        Log.e("tab",mFragments.size()+"。");
        viewModel.viewStatusLiveData.setValue(ViewStatus.SHOW_CONTENT);
        viewDataBinding.tabViewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        viewDataBinding.tabLayout.setupWithViewPager(viewDataBinding.tabViewPager);
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
