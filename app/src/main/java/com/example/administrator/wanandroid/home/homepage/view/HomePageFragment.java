package com.example.administrator.wanandroid.home.homepage.view;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.administrator.wanandroid.home.homepage.model.ArticleInfo;
import com.example.administrator.wanandroid.home.homepage.model.BannerInfo;
import com.example.administrator.wanandroid.home.homepage.presenter.HomePagePresenter;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment implements HomePageContract.View {
    private final static String TAG = "HomePageFragment";
    private RecyclerView recyclerView;
    private ViewPager viewPager;
    private List<ArticleInfo> mList = new ArrayList<>();
    private ArticlesAdapter mArticleAdapter;
    private HomePageContract.Presenter mPresenter;
    private HomePagePresenter homePagePresenter;
    private GetBannerInfoTask bannerInfoTask;
    private GetArticleInfoTask articleInfoTask;
    private Dialog mDialog;
    private View mView;
    private Integer page;
    private Context mContext;
    private List<BannerFragment> bannerList = new ArrayList<>();
    private BannerAdapter bannerAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = container.getContext();
        mView = inflater.inflate(R.layout.fragment_main_page,container,false);
        return mView;
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mArticleAdapter = new ArticlesAdapter(mList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View view = getActivity().findViewById(R.id.home_navigation_bar);
                if(!recyclerView.canScrollVertically(1)){
                    view.setVisibility(View.GONE);
                    page++;
                    mPresenter.getArticleInfo(page);
                }else {
                    if (view.getVisibility() == View.GONE){
                        view.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        recyclerView.setAdapter(mArticleAdapter);
        mDialog = new Dialog(mContext);
        mDialog.setTitle("加载中...");
        setPresenter(homePagePresenter);
        mPresenter.attachView(this);
        mPresenter.getArticleInfo(0);
        mPresenter.getBannerInfo();
    }

    @Override
    public void setArticleInfo(ArticleInfo info) {
        mList.add(info);
        mArticleAdapter.notifyDataSetChanged();
    }

    @Override
    public void setBannerInfo(BannerInfo info) {
        if (info.getErrorCode() == 0){
            int num =info.getData().size();
            for(int i = 0;i < num;i++){
                BannerFragment bannerFragment = new BannerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("url",info.getData().get(i).getImagePath());
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
    }
}
