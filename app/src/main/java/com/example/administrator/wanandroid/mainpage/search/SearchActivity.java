package com.example.administrator.wanandroid.mainpage.search;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.databinding.ActivitySearchBinding;
import com.example.administrator.wanandroid.mainpage.search.word.SearchWordFragment;

public class SearchActivity extends AppCompatActivity {

    private Fragment fragment;
    private ActivitySearchBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_search);
        fragment = new SearchWordFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.search_hot_word,fragment).commit();

    }
}
