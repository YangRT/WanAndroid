package com.example.administrator.wanandroid.mine.knowledge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.wanandroid.R;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.List;

public class KnowledgeAdapter extends BaseQuickAdapter<KnowledgeInfo.Data, BaseViewHolder> {

    private Context mContext;

    public KnowledgeAdapter(Context context,int layoutResId, @Nullable List<KnowledgeInfo.Data> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void convert(@NonNull final BaseViewHolder helper, KnowledgeInfo.Data item) {
        helper.setText(R.id.knowledge_item_title,item.getName());
        RecyclerView recyclerView = helper.getView(R.id.knowledge_item_recycler);
        List<KnowledgeInfo.Children> datas = item.getChildren();
        KnowledgeItemAdapter adapter = new KnowledgeItemAdapter(mContext,datas);
        recyclerView.setLayoutManager(new FlexboxLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    helper.itemView.callOnClick();
                }
                return false;
            }
        });
    }
}
