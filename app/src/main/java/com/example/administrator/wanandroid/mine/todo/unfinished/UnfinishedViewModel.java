package com.example.administrator.wanandroid.mine.todo.unfinished;

import com.example.administrator.wanandroid.base.MvvmBaseViewModel;
import com.example.administrator.wanandroid.mine.todo.TodoInfo;
import com.example.administrator.wanandroid.mine.todo.TodoModel;
import com.example.administrator.wanandroid.tab.TabModel;

public class UnfinishedViewModel extends MvvmBaseViewModel<TodoModel, TodoInfo.Datas> {

    public UnfinishedViewModel(){
        super();
        model = new TodoModel("unfinished");
        model.register(this);
    }

    public void loadTypeInfo(int type){
        model.getTypeInfo(type);
    }

    public void tryToLoadNextPage(){
        model.loadNextPage();
    }
}
