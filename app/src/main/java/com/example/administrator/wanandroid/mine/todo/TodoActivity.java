package com.example.administrator.wanandroid.mine.todo;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.databinding.ActivityTodoBinding;
import com.example.administrator.wanandroid.mine.todo.finish.FinishFragment;
import com.example.administrator.wanandroid.mine.todo.unfinished.UnfinishedFragment;

public class TodoActivity extends AppCompatActivity {

    private ActivityTodoBinding binding;
    private Fragment finish = new FinishFragment();
    private Fragment unfinished = new UnfinishedFragment();
    //上一个fragment
    Fragment from;
    //选中的fragment
    private int choseFragmentId = R.id.todo_unfinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_todo);

        getWindow().setStatusBarColor(Color.parseColor("#FCA019"));


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment, unfinished).commit();
        from = unfinished;

        binding.bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                choseFragmentId = item.getItemId();
                switch (item.getItemId()){
                    case R.id.todo_unfinished:
                        fragment = unfinished;
                        break;
                    case R.id.todo_finish:
                        fragment = finish;
                        break;
                }
                if(fragment == from){
                    Log.e("HomeActivity","same fragment");
                    return true;
                }
                switchFragment(from,fragment);
                from = fragment;
                invalidateOptionsMenu();
                return true;
            }
        });
    }


    //页面切换
    private void switchFragment(Fragment from,Fragment to){
        if(from != to){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if(!to.isAdded()){
                if(from != null){
                    transaction.hide(from);
                }
                if(to != null){
                    transaction.add(R.id.fragment,to).commit();
                }
            }else {
                if(from != null){
                    transaction.hide(from);
                }
                if(to != null){
                    transaction.show(to).commit();
                }
            }
        }
    }





}
