package com.example.administrator.wanandroid.mine;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.BaseListActivity;
import com.example.administrator.wanandroid.base.MvvmFragment;
import com.example.administrator.wanandroid.databinding.FragmentMineBinding;
import com.example.administrator.wanandroid.utils.BaseDataPreferenceUtil;
import com.example.administrator.wanandroid.viewstatus.EmptyCallBack;
import com.example.administrator.wanandroid.viewstatus.LoadingCallback;
import com.example.administrator.wanandroid.viewstatus.NetworkErrorCallback;
import com.example.administrator.wanandroid.viewstatus.ViewStatus;

import java.util.ArrayList;
import java.util.List;

public class MineFragment extends MvvmFragment<FragmentMineBinding,MineViewModel,MineInfo> {

    private MineAdapter mineAdapter;

    private List<MineItemInfo> itemInfos = new ArrayList<>();

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public MineViewModel getViewModel() {
        if(viewModel == null){
            viewModel = ViewModelProviders.of(this).get(MineViewModel.class);
        }
        return viewModel;
    }

    @Override
    public void onListItemInserted(ObservableArrayList<MineInfo> sender) {
        if(sender.size()>0 && sender.get(0).getErrorCode() == 0){
            if(sender.get(0).getData() != null) {
                viewDataBinding.mineLevel.setText("等级:"+sender.get(0).getData().getLevel());
                viewDataBinding.mineUsername.setText(BaseDataPreferenceUtil.getInstance().getLoginStatus());
                viewDataBinding.mineRank.setText(String.format("排名:%d", sender.get(0).getData().getRank()));
                viewDataBinding.minePoint.setText("积分:" + sender.get(0).getData().getCoinCount());
            }
        }else if(sender.size()>0 && sender.get(0).getErrorCode() == -1001){
            viewDataBinding.mineLevel.setText("等级:0");
            viewDataBinding.mineUsername.setText("未登录");
            viewDataBinding.mineRank.setText("排名:000");
            viewDataBinding.minePoint.setText("积分:0000");
        }
    }

    @Override
    protected String getFragmentTag() {
        return "mine";
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        setItemInfos();
        viewDataBinding.mineRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        viewDataBinding.mineRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mineAdapter = new MineAdapter(R.layout.mine_item,itemInfos);
        viewDataBinding.mineRecyclerview.setAdapter(mineAdapter);
        viewDataBinding.mineRecyclerview.setNestedScrollingEnabled(false);
        mineAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(position < 3){
                    if(BaseDataPreferenceUtil.getInstance().getLoginStatus() == null){
                        Toast.makeText(getContext(),"请先登录！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Intent intent = new Intent(getActivity(), BaseListActivity.class);
                intent.putExtra("type",((MineItemInfo)adapter.getData().get(position)).getTitle());
                if(position == 2){
                    intent.putExtra("count",viewDataBinding.minePoint.getText().toString());
                }
                getActivity().startActivity(intent);
            }
        });
    }

    public void refresh(){
        Log.e("mine","refresh");
        viewModel.tryToRefresh();
    }

    @Override
    public void onChanged(@Nullable Object o) {
        if(o instanceof ViewStatus && mLoadService != null){
            Log.e("mine","begin");
            switch ((ViewStatus) o){
                case LOADING:
                    mLoadService.showCallback(LoadingCallback.class);
                    break;
                case SHOW_CONTENT:
                    mLoadService.showSuccess();
                    refreshCancel();
                    break;
                case REQUEST_ERROR:
                    Log.e("mine","数据错误");
                    mLoadService.showSuccess();
                    Toast.makeText(getContext(),"请求失败,请检查网络！",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    protected void refreshCancel() {

    }

    @Override
    protected void onRetryBtnBack() {

    }

    private void setItemInfos(){
        MineItemInfo collect = new MineItemInfo();
        collect.setTitle("我的收藏");
        collect.setImage(R.drawable.collect);
        MineItemInfo share = new MineItemInfo();
        share.setTitle("我的分享");
        share.setImage(R.drawable.share);
        MineItemInfo point = new MineItemInfo();
        point.setTitle("积分明细");
        point.setImage(R.drawable.money);
        MineItemInfo rank = new MineItemInfo();
        rank.setTitle("积分排行");
        rank.setImage(R.drawable.ph);
        MineItemInfo knowledge = new MineItemInfo();
        knowledge.setTitle("知识体系");
        knowledge.setImage(R.drawable.knowledge);
        MineItemInfo wx = new MineItemInfo();
        wx.setTitle("公众号文章");
        wx.setImage(R.drawable.gzh);
        MineItemInfo navigation = new MineItemInfo();
        navigation.setTitle("导航");
        navigation.setImage(R.drawable.navigation);
        itemInfos.add(collect);
        itemInfos.add(share);
        itemInfos.add(point);
        itemInfos.add(rank);
        itemInfos.add(knowledge);
        itemInfos.add(wx);
        itemInfos.add(navigation);
    }
}
