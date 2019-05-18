package com.example.administrator.wanandroid.home.homepage.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.article.view.ArticleActivity;
import com.example.administrator.wanandroid.home.HomeActivity;
import com.example.administrator.wanandroid.home.homepage.model.ArticleInfo;

import java.util.List;
import java.util.Random;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {
    List<ArticleInfo.DataBean.DatasBean> mList;
    Context mContext;
    final static int[] images = {R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5,R.drawable.p6,R.drawable.p7,R.drawable.p8,R.drawable.p9,R.drawable.p10,R.drawable.p11,R.drawable.p12,R.drawable.p13,R.drawable.p14,R.drawable.p15,R.drawable.p16,R.drawable.p17,R.drawable.p18,R.drawable.p19,R.drawable.p20};
    public ArticlesAdapter(List<ArticleInfo.DataBean.DatasBean> list, Context context){
        mList = list;
        mContext = context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_page_recyclerview_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int n = position % 20;
        final int p = position;
        holder.tvTitle.setText(mList.get(position).getTitle());
        holder.tvTime.setText(mList.get(position).getNiceDate());
        holder.image.setImageResource(images[n]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ArticleActivity.class);
                intent.putExtra("url",mList.get(p).getLink());
                intent.putExtra("title",mList.get(p).getTitle());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvTime;
        ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.main_page_recyclerview_item_title);
            tvTime = itemView.findViewById(R.id.main_page_recyclerview_item_time);
            image = itemView.findViewById(R.id.main_page_recyclerview_item_image);
        }
    }
}
