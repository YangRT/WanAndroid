package com.example.administrator.wanandroid.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.home.homepage.view.HomePageFragment;
import com.example.administrator.wanandroid.home.knowledge.view.KnowledgeFragment;
import com.example.administrator.wanandroid.home.navigation.view.NavigationFragment;
import com.example.administrator.wanandroid.home.projects.view.ProjectsFragment;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    ViewPager mViewPager;
    List<Fragment> mList = new ArrayList<>();
    TextView tvMainPage;
    TextView tvKnowledge;
    TextView tvNavigation;
    TextView tvProjects;
    TextView tvUsername;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void init(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.main_page);
        mViewPager = findViewById(R.id.home_view_pager);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        tvMainPage = findViewById(R.id.home_main_page);
        tvKnowledge = findViewById(R.id.home_knowlege);
        tvNavigation = findViewById(R.id.home_navigation);
        tvProjects = findViewById(R.id.home_projects);
        tvMainPage.setOnClickListener(this);
        tvKnowledge.setOnClickListener(this);
        tvNavigation.setOnClickListener(this);
        tvProjects.setOnClickListener(this);
        mList.add(new HomePageFragment());
        mList.add(new KnowledgeFragment());
        mList.add(new NavigationFragment());
        mList.add(new ProjectsFragment());
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),mList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new ViewPagerChangeListener());
    }

    @Override
    public void onBackPressed() {
        drawer.openDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_main_page:
                changeBackground(0);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.home_knowlege:
                changeBackground(1);
                mViewPager.setCurrentItem(1);
                break;
            case R.id.home_navigation:
                changeBackground(2);
                mViewPager.setCurrentItem(2);
                break;
            case R.id.home_projects:
                changeBackground(3);
                mViewPager.setCurrentItem(3);
                break;

        }
    }

    class ViewPagerChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

        @Override
        public void onPageSelected(int position) { changeBackground(position); }

        @Override
        public void onPageScrollStateChanged(int state) {}
    }

    private void changeBackground(int position) {
        switch (position) {
            case 0:
                toolbar.setTitle(R.string.main_page);
                tvMainPage.setBackgroundResource(R.drawable.choiced_bound);
                tvKnowledge.setBackgroundResource(R.drawable.bar_bound);
                tvNavigation.setBackgroundResource(R.drawable.bar_bound);
                tvProjects.setBackgroundResource(R.drawable.bar_bound);
                break;
            case 1:
                toolbar.setTitle(R.string.knowledge);
                tvMainPage.setBackgroundResource(R.drawable.bar_bound);
                tvKnowledge.setBackgroundResource(R.drawable.choiced_bound);
                tvNavigation.setBackgroundResource(R.drawable.bar_bound);
                tvProjects.setBackgroundResource(R.drawable.bar_bound);
                break;
            case 2:
                toolbar.setTitle(R.string.navigtion);
                tvMainPage.setBackgroundResource(R.drawable.bar_bound);
                tvKnowledge.setBackgroundResource(R.drawable.bar_bound);
                tvNavigation.setBackgroundResource(R.drawable.choiced_bound);
                tvProjects.setBackgroundResource(R.drawable.bar_bound);
                break;
            case 3:
                toolbar.setTitle(R.string.projects);
                tvMainPage.setBackgroundResource(R.drawable.bar_bound);
                tvKnowledge.setBackgroundResource(R.drawable.bar_bound);
                tvNavigation.setBackgroundResource(R.drawable.bar_bound);
                tvProjects.setBackgroundResource(R.drawable.choiced_bound);
                break;
        }
    }
}
