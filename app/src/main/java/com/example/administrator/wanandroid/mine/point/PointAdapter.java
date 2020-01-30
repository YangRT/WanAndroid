package com.example.administrator.wanandroid.mine.point;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.wanandroid.R;

import java.util.List;

public class PointAdapter extends BaseQuickAdapter<PointInfo.Datas, BaseViewHolder> {

    public PointAdapter(int layoutResId, @Nullable List<PointInfo.Datas> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, PointInfo.Datas item) {
        helper
                .setText(R.id.point_reason,item.getReason())
                .setText(R.id.point_desc,item.getDesc())
                .setText(R.id.point_count,"+"+item.getCoinCount());
    }
}
