package com.example.administrator.wanandroid.base;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.databinding.ActivityListBinding;
import com.example.administrator.wanandroid.mainpage.search.article.SearchArticleFragment;
import com.example.administrator.wanandroid.mine.article.MyArticleFragment;
import com.example.administrator.wanandroid.mine.gzh.GzhFragment;
import com.example.administrator.wanandroid.mine.knowledge.KnowledgeFragment;
import com.example.administrator.wanandroid.mine.navigation.NavigationFragment;
import com.example.administrator.wanandroid.mine.point.PointFragment;
import com.example.administrator.wanandroid.mine.rank.RankFragment;
import com.example.administrator.wanandroid.square.share.ShareFragment;
import com.example.administrator.wanandroid.tab.TabFragment;

public class BaseListActivity extends AppCompatActivity {

    private ActivityListBinding binding;
    private String title;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_list);
        Intent intent = getIntent();
        title = intent.getStringExtra("type");
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolbar.setTitleTextAppearance(this,R.style.Toolbar_TitleText_low);
        binding.toolbar.setBackgroundColor(Color.parseColor("#FCA019"));
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorTheme));
        Bundle bundle = new Bundle();
        if(title.equals("我的收藏")){
            setTitleCenter(title);
            fragment = new MyArticleFragment();
            bundle.putString("type","collect");
        }else if(title.equals("我的分享")){
            setTitleCenter(title);
            fragment = new MyArticleFragment();
            bundle.putString("type","share");
        }else if(title.equals("积分排行")){
            setTitleCenter(title);
            fragment = new RankFragment();
        }else if(title.equals("积分明细")){
            setTitleCenter(title);
            fragment = new PointFragment();
            bundle.putString("count",intent.getStringExtra("count"));
        }else if(title.equals("知识体系")){
            setTitleCenter(title);
            fragment = new KnowledgeFragment();
        }else if(title.equals("导航")){
            setTitleCenter(title);
            fragment = new NavigationFragment();
        }else if(title.equals("公众号文章")){
            fragment = new TabFragment();
            bundle.putString("type","gzh");
            setTitleCenter("公众号");
        }else if(title.equals("项目分类")){
            setTitleCenter(title);
            fragment = new TabFragment();
            bundle.putString("type","classic");
        }else if(title.equals("search")){
            fragment = new SearchArticleFragment();
            setTitleCenter(intent.getStringExtra("key"));
            bundle.putString("key",intent.getStringExtra("key"));
        }else if(title.equals("分享文章")){
            setTitleCenter(title);
            fragment = new ShareFragment();
        }else {
            fragment = new TabFragment();
            bundle.putString("type",title);
            bundle.putStringArrayList("tabTitle",intent.getStringArrayListExtra("tabTitle"));
            bundle.putIntegerArrayList("tabId",intent.getIntegerArrayListExtra("tabId"));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
