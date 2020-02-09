package com.example.administrator.wanandroid.mine.todo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.databinding.ActivityTodoDetailBinding;
import com.example.administrator.wanandroid.utils.BaseDataPreferenceUtil;

public class TodoDetailActivity extends AppCompatActivity {

    private ActivityTodoDetailBinding binding;
    private String status;
    private TodoDetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_todo_detail);
        Intent intent = getIntent();
        status = intent.getStringExtra("status");

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("详情");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolbar.setTitleTextAppearance(this,R.style.Toolbar_TitleText_low);
        binding.toolbar.setBackgroundColor(Color.parseColor("#FCA019"));
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorTheme));
        setTitleCenter("详情");

        fragment = new TodoDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("status",status);
        if(!status.equals("add")){
            bundle.putSerializable("detail",intent.getSerializableExtra("detail"));
        }
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container,fragment).commit();
    }

    public void setTitleCenter(String text) {
        binding.toolbar.setTitle("title");
        for (int i = 0; i < binding.toolbar.getChildCount(); i++) {
            View view = binding.toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                if ("title".equals(textView.getText())) {
                    textView.setGravity(Gravity.CENTER);
                    Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.CENTER;
                    textView.setLayoutParams(params);
                }
            }
            binding.toolbar.setTitle(text);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_top,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }else if(item.getItemId() == R.id.todo_edit){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(status != null && status.equals("unfinished")){
            menu.findItem(R.id.todo_edit).setVisible(true);
        }else {
            menu.findItem(R.id.todo_edit).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }
}
