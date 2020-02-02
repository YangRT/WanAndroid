package com.example.administrator.wanandroid.square.share;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.BaseResponseInfo;
import com.example.administrator.wanandroid.databinding.FragmentShareBinding;
import com.example.administrator.wanandroid.net.NetUtil;
import com.example.administrator.wanandroid.net.UrlUtil;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ShareFragment extends Fragment implements View.OnClickListener{

    private FragmentShareBinding binding;
    private Disposable disposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_share,container,false);
        binding.btShare.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           case  R.id.bt_share:
            if(binding.shareLink.getText().toString().equals("") || binding.shareTitle.getText().toString().equals("")){
                Toast.makeText(getContext(),"请填齐信息！",Toast.LENGTH_SHORT).show();
            }else {
                shareArtilce(binding.shareTitle.getText().toString(),binding.shareLink.getText().toString());
            }
            break;
        }
    }

    private void shareArtilce(String title,String link){
        NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl)
                .create(ShareService.class)
                .getShareArticleInfo(title,link)
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
                            Toast.makeText(getContext(),"分享成功",Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }else {
                            Toast.makeText(getContext(),baseResponseInfo.getErrorMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(),"网络错误！",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(disposable != null){
            disposable.dispose();
        }
    }
}
