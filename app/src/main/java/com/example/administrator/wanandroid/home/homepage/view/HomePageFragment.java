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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.home.homepage.GetArticleInfoTask;
import com.example.administrator.wanandroid.home.homepage.GetBannerInfoTask;
import com.example.administrator.wanandroid.home.homepage.HomePageContract;
import com.example.administrator.wanandroid.home.homepage.adapter.ArticlesAdapter;
import com.example.administrator.wanandroid.home.homepage.adapter.BannerAdapter;
import com.example.administrator.wanandroid.home.homepage.adapter.RecyclerViewLines;
import com.example.administrator.wanandroid.home.homepage.model.ArticleInfo;
import com.example.administrator.wanandroid.home.homepage.model.BannerInfo;
import com.example.administrator.wanandroid.home.homepage.presenter.HomePagePresenter;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment implements HomePageContract.View,SwipeRefreshLayout.OnRefreshListener {
    private final static String TAG = "HomePageFragment";
    private RecyclerView recyclerView;
    private ViewPager viewPager;
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
    Boolean isRefresh = false;
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
        mPresenter.getBannerInfo();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init(){
        page = 0;
        bannerInfoTask = GetBannerInfoTask.getInstance();
        articleInfoTask = GetArticleInfoTask.getInstance();
        homePagePresenter = new HomePagePresenter(articleInfoTask,bannerInfoTask);
        viewPager = mView.findViewById(R.id.main_page_view_pager);
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
            for(int i = 0;i < num;i++){
                BannerFragment bannerFragment = new BannerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("imageUrl",info.getData().get(i).getImagePath());
                bundle.putString("articleUrl",info.getData().get(i).getUrl());
                bannerFragment.setArguments(bundle);
                bannerList.add(bannerFragment);
            }
        }
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        bannerAdapter = new BannerAdapter(fragmentManager,bannerList);
        viewPager.setAdapter(bannerAdapter);
        viewPager.setCurrentItem(0);
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
}
