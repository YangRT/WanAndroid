package com.example.administrator.wanandroid.home.homepage.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.example.administrator.wanandroid.home.homepage.view.BannerFragment;

import java.util.List;

public class ScrollViewPager extends ViewPager {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == SCROLL){
                setCurrentItem(getCurrentItem()+1);

            }
        }
    };


    private List<BannerFragment> mList;
    private int count;
    private final int SCROLL = 1;
    private Thread mThread;
    private int space;
    private boolean normal = true;



    public ScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public ScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(FragmentManager fragmentManager, final List<BannerFragment> f, final int space, final onViewPagerChangeListener listener){
            mList = f;
            count = f.size();
            this.space = space;
            setCurrentItem(1);
            setAdapter(new MyAdapter(fragmentManager));
            mThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        try {
                            normal = true;
                            Thread.sleep(space*1000);
                            handler.sendEmptyMessage(SCROLL);
                        } catch (InterruptedException e) {
                            try {
                                normal = false;
                                Thread.sleep(Integer.MAX_VALUE);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            e.printStackTrace();
                        }
                    }
                }
            });
            mThread.start();

            addOnPageChangeListener(new OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                        if(i == 0){
                            setCurrentItem(mList.size() - 2,false);
                            listener.onChange(mList.size()-2);
                        }else if(i == mList.size() - 1){
                            setCurrentItem(1,false);
                            listener.onChange(1);
                        }else{
                            listener.onChange(i);
                        }

                }

                @Override
                public void onPageScrollStateChanged(int i) {
                    if ((!normal && i != 1) || (normal &&i == 1)){
                        mThread.interrupt();
                    }
                }
            });
    }


    public class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return mList.get(i);
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}
