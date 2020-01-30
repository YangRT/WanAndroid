package com.example.administrator.wanandroid.mine.knowledge;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.ObservableArrayList;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.MvvmFragment;
import com.example.administrator.wanandroid.databinding.FragmentKnowledgeBinding;
import com.example.administrator.wanandroid.mine.MineViewModel;

public class KnowledgeFragment extends MvvmFragment<FragmentKnowledgeBinding,KnowledgeViewModel,KnowledgeInfo.Data> {
    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_knowledge;
    }

    @Override
    public KnowledgeViewModel getViewModel() {
        if(viewModel == null){
            viewModel = ViewModelProviders.of(this).get(KnowledgeViewModel.class);
        }
        return viewModel;
    }

    @Override
    public void onListItemInserted(ObservableArrayList<KnowledgeInfo.Data> sender) {

    }

    @Override
    protected String getFragmentTag() {
        return null;
    }

    @Override
    protected void refreshCancel() {

    }

    @Override
    protected void onRetryBtnBack() {

    }
}
