package com.example.administrator.wanandroid.mine.navigation;

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

public class NavigationItemAdapter extends RecyclerView.Adapter<NavigationItemAdapter.ViewHolder>{

    private Context mContext;
    private LayoutInflater mInflater;
    private List<NavigationInfo.Articles> list;
    private NavigationItemClickListener mListener;

    interface NavigationItemClickListener{

        void onCLick(String title,String link);
    }

    public void setNavigationItemClickListener(NavigationItemClickListener listener){
        mListener = listener;
    }

    public NavigationItemAdapter(Context context, List<NavigationInfo.Articles> datas){
        mContext = context;
        list = datas;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NavigationItemAdapter.ViewHolder(mInflater.inflate(R.layout.navigation_item_tv,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvTag.setText(list.get(i).getTitle());
        final int position = i;
        viewHolder.tvTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onCLick(list.get(position).getTitle(),list.get(position).getLink());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvTag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTag = itemView.findViewById(R.id.navigation_item_tv);
        }
    }

}
