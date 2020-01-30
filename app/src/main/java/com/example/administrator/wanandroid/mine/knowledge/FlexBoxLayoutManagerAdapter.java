package com.example.administrator.wanandroid.mine.knowledge;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.wanandroid.R;

import java.util.List;

public class FlexBoxLayoutManagerAdapter extends RecyclerView.Adapter<FlexBoxLayoutManagerAdapter.ViewHolder> {

    private List<KnowledgeInfo.Children> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;

    public FlexBoxLayoutManagerAdapter(Context context, List<KnowledgeInfo.Children> datas){
        mContext = context;
        mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.knowledge_flow_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTag.setText(mDatas.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvTag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTag = itemView.findViewById(R.id.tv_tag);
        }
    }
}
