package com.example.administrator.wanandroid.mainpage.search;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.BaseListActivity;
import com.example.administrator.wanandroid.customview.searchview.BCallBack;
import com.example.administrator.wanandroid.customview.searchview.ICallBack;
import com.example.administrator.wanandroid.customview.searchview.SearchView;
import com.example.administrator.wanandroid.databinding.ActivitySearchBinding;
import com.example.administrator.wanandroid.mainpage.search.word.SearchWordFragment;

public class SearchActivity extends AppCompatActivity {

    private Fragment fragment;
    private ActivitySearchBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_search);
        getWindow().setStatusBarColor(Color.parseColor("#FCA019"));
        binding.searchView.setOnClickBack(new BCallBack() {
            @Override
            public void backAction() {
                finish();
            }
        });
        binding.searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void searchAction(String text) {
                Log.e("Search",text);
                Intent intent = new Intent(SearchActivity.this, BaseListActivity.class);
                intent.putExtra("type","search");
                intent.putExtra("key",text);
                startActivity(intent);
            }
        });

    }
}
