package com.example.administrator.wanandroid.mine.todo.finish;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.MvvmFragment;
import com.example.administrator.wanandroid.databinding.FragmentTodoBinding;
import com.example.administrator.wanandroid.mine.todo.Event;
import com.example.administrator.wanandroid.mine.todo.TodoDetailActivity;
import com.example.administrator.wanandroid.mine.todo.TodoHelper;
import com.example.administrator.wanandroid.mine.todo.TodoInfo;
import com.example.administrator.wanandroid.mine.todo.TodoListAdapter;
import com.example.administrator.wanandroid.mine.todo.Type;
import com.example.administrator.wanandroid.mine.todo.unfinished.UnfinishedViewModel;

import java.util.ArrayList;

public class FinishFragment extends MvvmFragment<FragmentTodoBinding,FinishViewModel, TodoInfo.Datas> implements View.OnClickListener,TodoHelper.TodoListener, AdapterView.OnItemSelectedListener {

    private TodoListAdapter adapter;
    private TodoHelper helper;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        helper = new TodoHelper();
        helper.setTodoListener(this);
        viewDataBinding.articleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TodoListAdapter(R.layout.todo_item,new ArrayList<TodoInfo.Datas>());
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getViewModel().tryToLoadNextPage();
            }
        },viewDataBinding.articleRecyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TodoInfo.Datas data = (TodoInfo.Datas)(adapter.getData().get(position));
                Event event = new Event();
                event.setTitle(data.getTitle());
                event.setContent(data.getContent());
                event.setType(data.getType());
                event.setDate(data.getDateStr());
                Intent intent = new Intent(getActivity(), TodoDetailActivity.class);
                intent.putExtra("status","finish");
                intent.putExtra("detail",event);
                startActivity(intent);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId() == R.id.todo_item_delete){
                    TodoInfo.Datas datas = (TodoInfo.Datas)(adapter.getData().get(position));
                    Toast.makeText(getContext(),"点击完成:"+position,Toast.LENGTH_SHORT).show();
                    helper.deleteEvent(datas.getId(),position);
                }
            }
        });
        viewDataBinding.mainPageRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getViewModel().tryToRefresh();
            }
        });
        viewDataBinding.articleRecyclerView.setAdapter(adapter);
        viewDataBinding.articleRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        viewDataBinding.listSpinner.setOnItemSelectedListener(this);
        viewDataBinding.todoToolbarAdd.setOnClickListener(this);
        viewDataBinding.todoToolbarBack.setOnClickListener(this);
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_todo;
    }

    @Override
    public FinishViewModel getViewModel() {
        if(viewModel == null){
            viewModel = ViewModelProviders.of(this).get(FinishViewModel.class);
        }
        return viewModel;
    }

    @Override
    public void onListItemInserted(ObservableArrayList<TodoInfo.Datas> sender) {
        Log.e("Todo","frag length:"+sender.size());
        adapter.setNewData(sender);
    }

    @Override
    protected String getFragmentTag() {
        return "finish";
    }

    @Override
    protected void loadMoreFinish() {
        if(adapter.isLoading()){
            adapter.loadMoreComplete();
        }
    }

    @Override
    protected void loadMoreEmpty() {
        if(adapter.isLoading()){
            adapter.loadMoreEnd();
        }
    }

    @Override
    protected void loadMoreFail() {
        if(adapter.isLoading()){
            adapter.loadMoreFail();
        }
    }

    @Override
    protected boolean isRefreshing() {
        return viewDataBinding.mainPageRefreshLayout.isRefreshing();
    }

    @Override
    protected void refreshCancel() {
        if(viewDataBinding.mainPageRefreshLayout.isRefreshing()){
            viewDataBinding.mainPageRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void onRetryBtnBack() {

    }

    @Override
    public void onSuccess(int position, int type) {
        adapter.remove(position);
        Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFail(String msg, int type) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.todo_toolbar_back:
                getActivity().finish();
                break;
            case R.id.todo_toolbar_add:
                Intent intent = new Intent(getActivity(),TodoDetailActivity.class);
                intent.putExtra("status","add");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                getViewModel().loadTypeInfo(0);
                break;
            case Type.LIFE:
                getViewModel().loadTypeInfo(Type.LIFE);
                break;
            case Type.WORK :
                getViewModel().loadTypeInfo(Type.WORK);
                break;
            case Type.LEARN:
                getViewModel().loadTypeInfo(Type.LEARN);
                break;
            case Type.OTHER:
                getViewModel().loadTypeInfo(Type.OTHER);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
