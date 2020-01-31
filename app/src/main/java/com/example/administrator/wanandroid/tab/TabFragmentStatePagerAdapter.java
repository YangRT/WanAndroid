package com.example.administrator.wanandroid.tab;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.airbnb.lottie.L;

import java.util.List;

public class TabFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> list;
    private List<String> titles;

    public TabFragmentStatePagerAdapter(FragmentManager fm, List<Fragment> list, List<String> titles){
        super(fm);
        this.list = list;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
