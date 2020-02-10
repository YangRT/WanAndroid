package com.example.administrator.wanandroid.mine.todo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.wanandroid.R;

import java.util.List;

public class TodoListAdapter extends BaseQuickAdapter<TodoInfo.Datas, BaseViewHolder> {

    public TodoListAdapter(int layoutResId, @Nullable List<TodoInfo.Datas> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, TodoInfo.Datas item) {
        helper.setText(R.id.todo_item_title,item.getTitle());
        helper.setText(R.id.todo_item_time,"预计完成时间:"+item.getDateStr());
        switch (item.getType()){
            case Type.LIFE:
                helper.setText(R.id.todo_item_type,"生活");
                break;
            case Type.WORK:
                helper.setText(R.id.todo_item_type,"工作");
                break;
            case Type.LEARN:
                helper.setText(R.id.todo_item_type,"学习");
                break;
            case Type.OTHER:
                helper.setText(R.id.todo_item_type,"其他");
                break;
        }
        if(item.getStatus() == 1){
            helper.setText(R.id.todo_item_finish,"");
            helper.getView(R.id.todo_item_finish).setVisibility(View.GONE);
            helper.setText(R.id.todo_item_finish_time,"完成时间:"+item.getCompleteDateStr());
        }else {
            helper.setText(R.id.todo_item_finish_time,"");
            helper.addOnClickListener(R.id.todo_item_finish);
        }
        helper.addOnClickListener(R.id.todo_item_delete);
    }
}
