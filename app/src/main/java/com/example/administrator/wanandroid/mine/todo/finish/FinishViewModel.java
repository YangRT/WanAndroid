package com.example.administrator.wanandroid.mine.todo.finish;

import com.example.administrator.wanandroid.base.MvvmBaseViewModel;
import com.example.administrator.wanandroid.mine.todo.TodoInfo;
import com.example.administrator.wanandroid.mine.todo.TodoModel;

public class FinishViewModel extends MvvmBaseViewModel<TodoModel, TodoInfo.Datas> {

    public FinishViewModel(){
        super();
        model = new TodoModel("finish");
        model.register(this);
        model.getCachedDataAndLoad();
    }

    public void tryToLoadNextPage(){
        model.loadNextPage();
    }
}
