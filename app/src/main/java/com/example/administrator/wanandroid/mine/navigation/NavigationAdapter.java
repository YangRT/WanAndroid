package com.example.administrator.wanandroid.mine.navigation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.wanandroid.R;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.List;

public class NavigationAdapter extends BaseQuickAdapter<NavigationInfo.Data, BaseViewHolder> {

    private Context mContext;
    private NavigationAdapterListener mListener;

    public interface NavigationAdapterListener{
        void onItemClick(String title,String link,int id);
    }

    public void setItemClickListener(NavigationAdapterListener listener){
        mListener = listener;
    }

    public NavigationAdapter(Context context,int layoutResId, @Nullable List<NavigationInfo.Data> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(@NonNull final BaseViewHolder helper, final NavigationInfo.Data item) {
        helper.setText(R.id.navigation_item_title,item.getName());
        RecyclerView recyclerView = helper.getView(R.id.navigation_item_recyler_view);
        List<NavigationInfo.Articles> list = item.getArticles();
        NavigationItemAdapter adapter = new NavigationItemAdapter(mContext,list);
        adapter.setNavigationItemClickListener(new NavigationItemAdapter.NavigationItemClickListener() {
            @Override
            public void onCLick(String title, String link,int id) {
                if(mListener != null){
                    mListener.onItemClick(title,link,id);
                }
            }
        });
        recyclerView.setLayoutManager(new FlexboxLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        helper.addOnClickListener(R.id.navigation_item_recyler_view);
    }
}
