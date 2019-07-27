package com.example.administrator.wanandroid.home.homepage.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.home.homepage.GetArticleInfoTask;
import com.example.administrator.wanandroid.home.homepage.GetBannerInfoTask;
import com.example.administrator.wanandroid.home.homepage.HomePageContract;
import com.example.administrator.wanandroid.home.homepage.adapter.ArticlesAdapter;
import com.example.administrator.wanandroid.home.homepage.adapter.BannerAdapter;
import com.example.administrator.wanandroid.home.homepage.adapter.RecyclerViewLines;
import com.example.administrator.wanandroid.home.homepage.adapter.ScrollViewPager;
import com.example.administrator.wanandroid.home.homepage.adapter.onViewPagerChangeListener;
import com.example.administrator.wanandroid.home.homepage.model.ArticleInfo;
import com.example.administrator.wanandroid.home.homepage.model.BannerInfo;
import com.example.administrator.wanandroid.home.homepage.presenter.HomePagePresenter;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment implements HomePageContract.View,SwipeRefreshLayout.OnRefreshListener {
    private final static String TAG = "HomePageFragment";
    private RecyclerView recyclerView;
    private ScrollViewPager viewPager;
    private List<ImageView> points;
    private List<ArticleInfo.DataBean.DatasBean> mList = new ArrayList<>();
    private ArticlesAdapter mArticleAdapter;
    private HomePageContract.Presenter mPresenter;
    private HomePagePresenter homePagePresenter;
    private GetBannerInfoTask bannerInfoTask;
    private GetArticleInfoTask articleInfoTask;
    private AlertDialog mDialog;
    private View mView;
    private FloatingActionButton topButton;
    private Integer page;
    private Context mContext;
    private RecyclerViewLines lines;
    private List<BannerFragment> bannerList = new ArrayList<>();
    private BannerAdapter bannerAdapter;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayout pointLayout;
    Boolean isRefresh = false;
    Boolean firstLoading = true;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.fragment_main_page,container,false);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("FragmentHome","onResume()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(firstLoading){
            init();
            firstLoading = false;
        }
        Log.e("FragmentHome","onActivityCreated");
    }

    private void init(){
        page = 0;
        bannerInfoTask = GetBannerInfoTask.getInstance();
        articleInfoTask = GetArticleInfoTask.getInstance();
        homePagePresenter = new HomePagePresenter(articleInfoTask,bannerInfoTask);
        viewPager = mView.findViewById(R.id.main_page_view_pager);
        pointLayout = mView.findViewById(R.id.points_layout);
        recyclerView = mView.findViewById(R.id.article_recycler_view);
        refreshLayout = mView.findViewById(R.id.main_page_refresh_layout);
        topButton = mView.findViewById(R.id.main_page_float_button);
        refreshLayout.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        lines = new RecyclerViewLines(mContext);
        mArticleAdapter = new ArticlesAdapter(mList,mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(lines);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0){
                    topButton.hide();
                }else {
                    if(topButton.getVisibility() == View.GONE){
                        topButton.show();
                    }
                }
                View view = getActivity().findViewById(R.id.home_navigation_bar);
                if(!recyclerView.canScrollVertically(1)){
                    view.setVisibility(View.GONE);
                    page++;
                    mPresenter.getArticleInfo(page);
                }
                else {
                    if (view.getVisibility() == View.GONE){
                        view.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        recyclerView.setAdapter(mArticleAdapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("正在加载...");
        mDialog = builder.create();
        setPresenter(homePagePresenter);
        mPresenter.attachView(this);
        mPresenter.getArticleInfo(0);
        mPresenter.getBannerInfo();
    }

    @Override
    public void setArticleInfo(ArticleInfo info) {
        if(isRefresh){
            mList.clear();
        }
        int l = mList.size();
        for(int i = 0;i < info.getData().getDatas().size();i++)
            mList.add(info.getData().getDatas().get(i));
        if(isRefresh){
            mArticleAdapter.notifyDataSetChanged();
            isRefresh = false;
        }else{
            mArticleAdapter.notifyItemRangeInserted(l,20);
        }
    }

    @Override
    public void setBannerInfo(BannerInfo info) {
        if (info.getErrorCode() == 0){
            int num =info.getData().size();
            BannerFragment banner = new BannerFragment();
            Bundle b = new Bundle();
            b.putString("imageUrl",info.getData().get(num-1).getImagePath());
            b.putString("articleUrl",info.getData().get(num-1).getUrl());
            b.putString("title",info.getData().get(num-1).getTitle());
            banner.setArguments(b);
            bannerList.add(banner);
            for(int i = 0;i < num;i++){
                BannerFragment bannerFragment = new BannerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("imageUrl",info.getData().get(i).getImagePath());
                bundle.putString("articleUrl",info.getData().get(i).getUrl());
                bundle.putString("title",info.getData().get(i).getTitle());
                bannerFragment.setArguments(bundle);
                bannerList.add(bannerFragment);
            }
            BannerFragment f = new BannerFragment();
            Bundle bundle = new Bundle();
            bundle.putString("imageUrl",info.getData().get(0).getImagePath());
            bundle.putString("articleUrl",info.getData().get(0).getUrl());
            bundle.putString("title",info.getData().get(0).getTitle());
            f.setArguments(bundle);
            bannerList.add(f);
            initPoint(bannerList.size());
            points.get(0).setBackgroundResource(R.drawable.choiced_point);
            viewPager.init(getChildFragmentManager(), bannerList, 2, new onViewPagerChangeListener() {
                @Override
                public void onChange(int currentPage) {
                    for(int i = 0;i < points.size();i++){
                        if(i == currentPage -1){
                            points.get(i).setBackgroundResource(R.drawable.choiced_point);
                        }else{
                            points.get(i).setBackgroundResource(R.drawable.point);
                        }

                    }
                }
            });
            viewPager.setCurrentItem(1);
        }



    }

    private void initPoint(int size) {
        points = new ArrayList<>();
        points.clear();
        for(int i = 0; i< size-2;i++){
            ImageView imageView = new ImageView(mContext);
            imageView.setBackgroundResource(R.drawable.point);
            LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(12,12);
            params.setMargins(0,0,10,0);
            points.add(imageView);
            pointLayout.addView(imageView,params);
        }
    }

    @Override
    public void setPresenter(HomePageContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading() {
        if(mDialog != null){
            mDialog.show();
        }
    }

    @Override
    public void showError(String e) {
        Toast.makeText(mContext,e,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        if (mDialog.isShowing()){
            mDialog.dismiss();
        }
        if (refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
    }


    @Override
    public void onRefresh() {
        if(refreshLayout.isRefreshing()){
            page = 0;
            isRefresh = true;
            mPresenter.getArticleInfo(page);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("FragmentHome","onStart()");
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.e("FragmentHome","onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("FragmentHome","onStop()");
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
        Log.e("FragmentHome","onDestroy()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("FragmentHome","onCreate()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("FragmentHome","onDetach()");
    }
}
