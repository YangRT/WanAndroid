package com.example.administrator.wanandroid.mine.rank;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.wanandroid.R;

import java.util.List;

public class RankAdapter extends BaseQuickAdapter<RankInfo.Datas, BaseViewHolder> {


    public RankAdapter(int layoutResId, @Nullable List<RankInfo.Datas> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RankInfo.Datas item) {
        helper
                .setText(R.id.rank_num,item.getRank()+"")
                .setText(R.id.rank_username,item.getUsername())
                .setText(R.id.rank_point,item.getCoinCount()+"");
    }
}
