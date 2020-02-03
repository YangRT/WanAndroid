package com.example.administrator.wanandroid.mine.navigation;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.BaseCustomViewModel;
import com.example.administrator.wanandroid.base.MvvmFragment;
import com.example.administrator.wanandroid.databinding.FragmentNavigationBinding;
import com.example.administrator.wanandroid.mainpage.ArticleActivity;
import com.example.administrator.wanandroid.mine.SpaceItemDecoration;

import java.util.ArrayList;

public class NavigationFragment extends MvvmFragment<FragmentNavigationBinding,NavigationViewModel,NavigationInfo.Data> {

    private NavigationAdapter navigationAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewDataBinding.navigationRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        viewDataBinding.navigationRecyclerview.addItemDecoration(new SpaceItemDecoration(10));
        navigationAdapter = new NavigationAdapter(getContext(),R.layout.navigation_item,new ArrayList<NavigationInfo.Data>());
        viewDataBinding.navigationRecyclerview.setAdapter(navigationAdapter);
        navigationAdapter.setItemClickListener(new NavigationAdapter.NavigationAdapterListener() {
            @Override
            public void onItemClick(String title, String link,int id) {
                Toast.makeText(getContext(),title,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra("url",link);
                intent.putExtra("title",title);
                intent.putExtra("id",id);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    public NavigationViewModel getViewModel() {
        if(viewModel == null){
            viewModel = ViewModelProviders.of(this).get(NavigationViewModel.class);
        }
        return viewModel;
    }

    @Override
    public void onListItemInserted(ObservableArrayList<NavigationInfo.Data> sender) {
        navigationAdapter.setNewData(sender);
    }

    @Override
    protected String getFragmentTag() {
        return "navigation";
    }

    @Override
    protected void refreshCancel() {

    }

    @Override
    protected void onRetryBtnBack() {

    }
}
