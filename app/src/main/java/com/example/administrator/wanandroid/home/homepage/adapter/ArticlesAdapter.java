package com.example.administrator.wanandroid.home.homepage.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.home.homepage.model.ArticleInfo;

import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {
    List<ArticleInfo> mList;
    public ArticlesAdapter(List<ArticleInfo> list){
        mList = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int p = position / 20;
        int n = position % 20;
        holder.tvAuthor.setText(mList.get(p).getData().getDatas().get(n).getAuthor());
        holder.tvClass.setText(String.format("%s/%s", mList.get(p).getData().getDatas().get(n).getSuperChapterName(), mList.get(p).getData().getDatas().get(n).getChapterName()));
        holder.tvTitle.setText(mList.get(p).getData().getDatas().get(n).getTitle());
        holder.tvTime.setText(mList.get(p).getData().getDatas().get(n).getNiceDate());
        if(mList.get(p).getData().getDatas().get(n).isCollect()){
            holder.imageCollect.setImageResource(R.drawable.article_collected);
        }else {
            holder.imageCollect.setImageResource(R.drawable.article_not_collect);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size()*20;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvAuthor;
        TextView tvClass;
        TextView tvTitle;
        TextView tvTime;
        ImageView imageCollect;
        public ViewHolder(View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.article_item_author);
            tvClass = itemView.findViewById(R.id.article_item_class);
            tvTitle = itemView.findViewById(R.id.article_item_title);
            tvTime = itemView.findViewById(R.id.article_item_time);
            imageCollect = itemView.findViewById(R.id.article_item_collect_image);

        }
    }
}
