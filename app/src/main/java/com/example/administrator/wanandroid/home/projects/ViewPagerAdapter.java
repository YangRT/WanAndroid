package com.example.administrator.wanandroid.home.projects;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.administrator.wanandroid.home.projects.model.TitlesInfo;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> list;
    private TitlesInfo titles;
    public ViewPagerAdapter(FragmentManager fm, List<Fragment> list, TitlesInfo titles) {
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
        return titles.getData().get(position).getName();
    }
}
