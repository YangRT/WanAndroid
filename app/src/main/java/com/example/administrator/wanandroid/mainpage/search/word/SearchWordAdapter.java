package com.example.administrator.wanandroid.mainpage.search.word;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.mine.knowledge.KnowledgeInfo;
import com.example.administrator.wanandroid.mine.knowledge.KnowledgeItemAdapter;

import java.util.List;

public class SearchWordAdapter extends RecyclerView.Adapter<SearchWordAdapter.ViewHolder>{


    private List<SearchWordInfo.Data> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;
    private OnClickListener listener;

    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

    interface OnClickListener{
        void onClick(String word);
    }

    public SearchWordAdapter(Context context, List<SearchWordInfo.Data> datas){
        mContext = context;
        mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SearchWordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchWordAdapter.ViewHolder(mInflater.inflate(R.layout.navigation_item_tv,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchWordAdapter.ViewHolder holder, int position) {
        final String word = mDatas.get(position).getName();
        holder.tvTag.setText(word);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(word);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvTag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTag = itemView.findViewById(R.id.navigation_item_tv);
        }
    }
}
