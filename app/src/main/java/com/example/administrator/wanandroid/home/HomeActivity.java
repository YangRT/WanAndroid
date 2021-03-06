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
import com.example.administrator.wanandroid.base.BackTopEvent;
import com.example.administrator.wanandroid.base.BaseListActivity;
import com.example.administrator.wanandroid.login.LoginActivity;
import com.example.administrator.wanandroid.base.BaseResponseInfo;
import com.example.administrator.wanandroid.mainpage.MainPageFragment;
import com.example.administrator.wanandroid.databinding.ActivityHomeBinding;
import com.example.administrator.wanandroid.mainpage.search.SearchActivity;
import com.example.administrator.wanandroid.mine.MineFragment;
import com.example.administrator.wanandroid.mine.todo.TodoActivity;
import com.example.administrator.wanandroid.net.NetUtil;
import com.example.administrator.wanandroid.net.UrlUtil;
import com.example.administrator.wanandroid.square.SquareFragment;
import com.example.administrator.wanandroid.project.ProjectFragment;
import com.example.administrator.wanandroid.utils.BaseDataPreferenceUtil;
import com.example.administrator.wanandroid.utils.ClearCacheUtil;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity {

    private final static String TAG = "HomeActivity";

    ActivityHomeBinding binding;
    //主页4个fragment
    MineFragment mineFragment = new MineFragment();
    ProjectFragment projectFragment = new ProjectFragment();
    SquareFragment squareFragment = new SquareFragment();
    MainPageFragment mainPageFragment = new MainPageFragment();
    //上一个fragment
    Fragment from;
    //选中的fragment
    private int choseFragmentId = R.id.main;
    private Disposable disposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate");
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("");
        binding.toolbarTitle.setText("首页");
        //setTitleCenter("首页测试长度");
        binding.toolbar.setTitleTextAppearance(this,R.style.Toolbar_TitleText_low);
        binding.toolbar.setBackgroundColor(Color.parseColor("#FCA019"));
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorTheme));
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
                    BackTopEvent backTopEvent = new BackTopEvent();
                    backTopEvent.id = item.getItemId();
                    EventBus.getDefault().post(backTopEvent);
                    return true;
                }
                switchFragment(from,fragment);
                if(getSupportActionBar() != null){
                    binding.toolbarTitle.setText(item.getTitle());

                }
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
            case R.id.action_write:
                if(BaseDataPreferenceUtil.getInstance().getLoginStatus() == null){
                    Toast.makeText(this,"请先登录！",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent4 = new Intent(HomeActivity.this, TodoActivity.class);
                    startActivity(intent4);
                }
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
               menu.findItem(R.id.action_write).setVisible(false);
                break;
            case R.id.square:
                menu.findItem(R.id.action_search).setVisible(false);
                menu.findItem(R.id.action_add).setVisible(true);
                menu.findItem(R.id.action_project).setVisible(false);
                menu.findItem(R.id.action_mine).setVisible(false);
                menu.findItem(R.id.action_exit).setVisible(false);
                menu.findItem(R.id.action_write).setVisible(false);
                break;
            case R.id.project:
                menu.findItem(R.id.action_search).setVisible(false);
                menu.findItem(R.id.action_add).setVisible(false);
                menu.findItem(R.id.action_project).setVisible(true);
                menu.findItem(R.id.action_mine).setVisible(false);
                menu.findItem(R.id.action_exit).setVisible(false);
                menu.findItem(R.id.action_write).setVisible(false);
                break;
            case R.id.mine:
                menu.findItem(R.id.action_search).setVisible(false);
                menu.findItem(R.id.action_add).setVisible(false);
                menu.findItem(R.id.action_project).setVisible(false);
                menu.findItem(R.id.action_write).setVisible(true);
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


    //退出登录
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
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onCreate");
        if(disposable != null){
            disposable.dispose();
        }
    }
}
