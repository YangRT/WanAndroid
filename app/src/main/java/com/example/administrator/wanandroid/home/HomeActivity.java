package com.example.administrator.wanandroid.home;

import android.content.Intent;
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
import android.widget.Toast;


import com.example.administrator.wanandroid.MyApplication;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.BaseListActivity;
import com.example.administrator.wanandroid.login.LoginActivity;
import com.example.administrator.wanandroid.base.BaseResponseInfo;
import com.example.administrator.wanandroid.mainpage.MainPageFragment;
import com.example.administrator.wanandroid.databinding.ActivityHomeBinding;
import com.example.administrator.wanandroid.mainpage.search.SearchActivity;
import com.example.administrator.wanandroid.mine.MineFragment;
import com.example.administrator.wanandroid.mine.MineItemInfo;
import com.example.administrator.wanandroid.net.NetUtil;
import com.example.administrator.wanandroid.net.UrlUtil;
import com.example.administrator.wanandroid.square.SquareFragment;
import com.example.administrator.wanandroid.project.ProjectFragment;
import com.example.administrator.wanandroid.utils.BaseDataPreferenceUtil;
import com.example.administrator.wanandroid.utils.ClearCacheUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity {

    private final static String TAG = "HomeActivity";

    ActivityHomeBinding binding;
    MineFragment mineFragment = new MineFragment();
    ProjectFragment projectFragment = new ProjectFragment();
    SquareFragment squareFragment = new SquareFragment();
    MainPageFragment mainPageFragment = new MainPageFragment();
    Fragment from;
    private int choseFragmentId = R.id.main;
    private Disposable disposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate");
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("首页");
        setTitleCenter("首页");
        binding.toolbar.setTitleTextAppearance(this,R.style.Toolbar_TitleText_low);
        binding.toolbar.setBackgroundColor(Color.parseColor("#FCA019"));
        getWindow().setStatusBarColor(Color.parseColor("#FCA019"));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment, mainPageFragment).commit();
        from = mainPageFragment;

        binding.bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                choseFragmentId = item.getItemId();
                switch (item.getItemId()){
                    case R.id.main:
                        fragment = mainPageFragment;
                        break;
                    case R.id.square:
                        fragment = squareFragment;
                        break;
                    case R.id.project:
                        fragment = projectFragment;
                        break;
                    case R.id.mine:
                        fragment = mineFragment;
                        break;
                }
                if(fragment == from){
                    Log.e("HomeActivity","same fragment");
                    return true;
                }
                switchFragment(from,fragment);
                if(getSupportActionBar() != null){
                    setTitleCenter(item.getTitle().toString());
                }
                from = fragment;
                invalidateOptionsMenu();
                return true;
            }
        });
    }

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
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_search:
                Log.e("HomeActivity","Search");
                Intent intent2 = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent2);
                break;
            case R.id.action_add:
                Intent intent3 = new Intent(HomeActivity.this,BaseListActivity.class);
                intent3.putExtra("type","分享文章");
                Log.e("HomeActivity","Add");
                startActivity(intent3);
                break;
            case R.id.action_project:
                Intent intent1 = new Intent(HomeActivity.this,BaseListActivity.class);
                intent1.putExtra("type","项目分类");
                startActivity(intent1);
                break;
            case R.id.action_mine:
                Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.action_exit:
                exit();
                break;
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (choseFragmentId){
            case R.id.main:
               menu.findItem(R.id.action_search).setVisible(true);
               menu.findItem(R.id.action_add).setVisible(false);
               menu.findItem(R.id.action_project).setVisible(false);
               menu.findItem(R.id.action_mine).setVisible(false);
               menu.findItem(R.id.action_exit).setVisible(false);
                break;
            case R.id.square:
                menu.findItem(R.id.action_search).setVisible(false);
                menu.findItem(R.id.action_add).setVisible(true);
                menu.findItem(R.id.action_project).setVisible(false);
                menu.findItem(R.id.action_mine).setVisible(false);
                menu.findItem(R.id.action_exit).setVisible(false);
                break;
            case R.id.project:
                menu.findItem(R.id.action_search).setVisible(false);
                menu.findItem(R.id.action_add).setVisible(false);
                menu.findItem(R.id.action_project).setVisible(true);
                menu.findItem(R.id.action_mine).setVisible(false);
                menu.findItem(R.id.action_exit).setVisible(false);
                break;
            case R.id.mine:
                menu.findItem(R.id.action_search).setVisible(false);
                menu.findItem(R.id.action_add).setVisible(false);
                menu.findItem(R.id.action_project).setVisible(false);
                if(BaseDataPreferenceUtil.getInstance().getLoginStatus() != null){
                    menu.findItem(R.id.action_exit).setVisible(true);
                    menu.findItem(R.id.action_mine).setVisible(false);
                }else {
                    menu.findItem(R.id.action_exit).setVisible(false);
                    menu.findItem(R.id.action_mine).setVisible(true);
                }
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void exit(){
        NetUtil.getInstance().getLoginRetrofitInstance(UrlUtil.baseUrl)
                .create(ExitService.class)
                .getExitInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponseInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(BaseResponseInfo baseResponseInfo) {
                        if(baseResponseInfo.getErrorCode() == 0){
                            Toast.makeText(HomeActivity.this,"退出成功！",Toast.LENGTH_SHORT).show();
                            BaseDataPreferenceUtil.getInstance().saveLoginStatus(null);
                            invalidateOptionsMenu();
                            ClearCacheUtil.cleanApplicationData(MyApplication.getContext());
                            mineFragment.refresh();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(HomeActivity.this,"请求失败！",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onStart() {
        Log.e(TAG,"onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.e(TAG,"onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.e(TAG,"onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e(TAG,"onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e(TAG,"onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onCreate");
        if(disposable != null){
            disposable.dispose();
        }
    }
}
