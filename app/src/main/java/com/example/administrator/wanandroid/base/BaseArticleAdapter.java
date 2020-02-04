package com.example.administrator.wanandroid.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.BaseCustomViewModel;

import java.util.List;
//文章列表通用适配器
public class BaseArticleAdapter extends BaseMultiItemQuickAdapter<BaseCustomViewModel, BaseViewHolder> {

    private Context mContext;

    public BaseArticleAdapter(Context context, @Nullable List<BaseCustomViewModel> data) {
        super(data);
        mContext = context;
        addItemType(BaseCustomViewModel.NORMAL,R.layout.article_item);
        addItemType(BaseCustomViewModel.WITH_PIC,R.layout.article_item_with_pic);
        addItemType(BaseCustomViewModel.PROJECT,R.layout.article_item_project);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BaseCustomViewModel item) {
        if(helper.getItemViewType() == BaseCustomViewModel.WITH_PIC){
            Glide.with(mContext).load(item.getPath()).into((ImageView) helper.itemView.findViewById(R.id.main_page_recyclerview_item_image));
        }else if(helper.getItemViewType() == BaseCustomViewModel.PROJECT){
            Glide.with(mContext).load(item.getPath()).into((ImageView) helper.itemView.findViewById(R.id.main_page_recyclerview_item_image));
            helper.setText(R.id.main_page_recyclerview_item_desc,item.getDescription());
        }
        helper
                .setText(R.id.main_page_recyclerview_item_title,item.getTitle())
                .setText(R.id.main_page_recyclerview_item_time,item.getTime())
                .setText(R.id.main_page_recyclerview_item_author,item.getAuthor())
                .setText(R.id.main_page_recyclerview_item_type,item.getClassic());
        helper.addOnClickListener(R.id.main_page_recyclerview_item_collect);
        if(item.isCollect()){
            helper.setImageResource(R.id.main_page_recyclerview_item_collect,R.drawable.like);
        }else {
            helper.setImageResource(R.id.main_page_recyclerview_item_collect,R.drawable.unlike);
        }

    }

}
