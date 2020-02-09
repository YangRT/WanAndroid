package com.example.administrator.wanandroid.mine.todo;

import com.example.administrator.wanandroid.base.BaseResponseInfo;
import com.example.administrator.wanandroid.net.NetUtil;
import com.example.administrator.wanandroid.net.UrlUtil;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TodoHelper {

    public final static int DELETE = 1;
    public final static int MODIFY = 2;
    public final static int CHANGE = 3;
    public final static int ADD = 4;

    private TodoListener listener;

    public void setTodoListener(TodoListener listener){
        this.listener = listener;
    }

    public interface TodoListener{

        void onSuccess(int position,int type);
        void onFail(String msg,int type);
    }

    public void deleteEvent(int id, final int position){
        NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl)
                .create(TodoService.class)
                .getDeleteResponse(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponseInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponseInfo baseResponseInfo) {
                        if(baseResponseInfo.getErrorCode() == 0){
                            if(listener != null){
                                listener.onSuccess(position,DELETE);
                            }
                        }else {
                            if(listener != null){
                                listener.onFail(baseResponseInfo.getErrorMsg(),DELETE);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(listener != null){
                            listener.onFail(e.getMessage(),DELETE);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void modifyEvent(Event event,int id){
        NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl)
                .create(TodoService.class)
                .getUpdateEventResponse(id,event.getTitle(),event.getContent(),event.getDate(),event.getType())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponseInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponseInfo baseResponseInfo) {
                        if(baseResponseInfo.getErrorCode() == 0){
                            if(listener != null){
                                listener.onSuccess(-1,MODIFY);
                            }
                        }else {
                            if(listener != null){
                                listener.onFail(baseResponseInfo.getErrorMsg(),MODIFY);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(listener != null){
                            listener.onFail(e.getMessage(),MODIFY);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void ChangeStatus(int id, final int position){
        NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl)
                .create(TodoService.class)
                .getChangeStatusResponse(id,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponseInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponseInfo baseResponseInfo) {
                        if(baseResponseInfo.getErrorCode() == 0){
                            if(listener != null){
                                listener.onSuccess(position,CHANGE);
                            }
                        }else {
                            if(listener != null){
                                listener.onFail(baseResponseInfo.getErrorMsg(),CHANGE);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(listener != null){
                            listener.onFail(e.getMessage(),CHANGE);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void addEvent(Event event){
        NetUtil.getInstance().getRetrofitInstance(UrlUtil.baseUrl)
                .create(TodoService.class)
                .getAddEventResponse(event.getTitle(),event.getContent(),event.getDate(),event.getType())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponseInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponseInfo baseResponseInfo) {
                        if(baseResponseInfo.getErrorCode() == 0){
                            if(listener != null){
                                listener.onSuccess(-1,ADD);
                            }
                        }else {
                            if(listener != null){
                                listener.onFail(baseResponseInfo.getErrorMsg(),ADD);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(listener != null){
                            listener.onFail(e.getMessage(),ADD);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
