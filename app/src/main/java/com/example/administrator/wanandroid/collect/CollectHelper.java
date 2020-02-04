package com.example.administrator.wanandroid.collect;

import com.example.administrator.wanandroid.base.BaseResponseInfo;
import com.example.administrator.wanandroid.net.NetUtil;
import com.example.administrator.wanandroid.net.UrlUtil;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

// 收藏文章工具类
public class CollectHelper {

    private CollectCallBackListener mListener;



    public void addCollectArticle(int id, final int position){
        NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl)
                .create(CollectService.class)
                .getCollectResponse(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponseInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponseInfo baseResponseInfo) {
                        if(baseResponseInfo.getErrorCode() == 0){
                            if(mListener != null){
                                mListener.onCollectSuccess(position);
                            }
                        }else {
                            if(mListener != null){
                                mListener.onFail(baseResponseInfo.getErrorMsg(),0);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(mListener != null){
                            mListener.onFail("网络错误！",0);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void unCollectArticle(int id,final int position){
            NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl)
                    .create(CollectService.class)
                    .getUnCollectResponse(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponseInfo>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponseInfo baseResponseInfo) {
                            if(baseResponseInfo.getErrorCode() == 0){
                                if(mListener != null){
                                    mListener.onUnCollectSuccess(position);
                                }
                            }else {
                                if(mListener != null){
                                    mListener.onFail(baseResponseInfo.getErrorMsg(),1);
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if(mListener != null){
                                mListener.onFail("网络错误！",1);
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
    }

    public void unCollectArticle(int id,int originId,final int position){
            NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl)
                    .create(CollectService.class)
                    .getUnCollectInMineResponse(id,originId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponseInfo>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponseInfo baseResponseInfo) {
                            if(baseResponseInfo.getErrorCode() == 0){
                                if(mListener != null){
                                    mListener.onUnCollectSuccess(position);
                                }
                            }else {
                                if(mListener != null){
                                    mListener.onFail(baseResponseInfo.getErrorMsg(),1);
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if(mListener != null){
                                mListener.onFail("网络错误！",1);
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
    }

    public interface CollectCallBackListener{
        void onCollectSuccess(int position);
        void onUnCollectSuccess(int position);
        void onFail(String message,int type);
    }

    public void setCollectCallBackListener(CollectCallBackListener listener){
        this.mListener = listener;
    }
}
