package com.example.administrator.wanandroid.home.homepage.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.wanandroid.home.homepage.view.BannerFragment;

import java.util.List;

public class BannerAdapter extends FragmentPagerAdapter {
    List<BannerFragment> mList;
    public BannerAdapter(FragmentManager fm, List<BannerFragment> list){
        super(fm);
        mList = list;

    }
    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
