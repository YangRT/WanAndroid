package com.example.administrator.wanandroid.mine;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.wanandroid.R;

import java.util.List;

public class MineAdapter extends BaseQuickAdapter<MineItemInfo, BaseViewHolder> {

    public MineAdapter(int layoutResId, @Nullable List<MineItemInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MineItemInfo item) {
        helper.setText(R.id.mine_item_title,item.getTitle())
                .setImageResource(R.id.mine_item_icon,item.getImage());
    }
}
