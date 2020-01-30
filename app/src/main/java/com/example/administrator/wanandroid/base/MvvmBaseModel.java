package com.example.administrator.wanandroid.base;

import android.text.TextUtils;
import android.util.Log;

import com.example.administrator.wanandroid.utils.BaseDataPreferenceUtil;
import com.example.administrator.wanandroid.utils.GsonUtil;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.reactivex.disposables.CompositeDisposable;

public abstract class MvvmBaseModel<T> {


    private final static String TAG = "MvvmBaseModel";

    protected CompositeDisposable compositeDisposable;
    protected ReferenceQueue<IBaseModelListener> listenerQueue;
    protected ConcurrentLinkedQueue<WeakReference<IBaseModelListener>> listenerLinkedQueue;
    private BaseCacheData<T> mData;
    protected boolean isRefreshing;
    protected String mCachedPreferenceKey;
    private String mApkPreferenceData;
    protected int pageNum = 0;
    protected boolean mIsPaging;

    public MvvmBaseModel(boolean isPaging,String key,String data){
        this.mIsPaging = isPaging;
        this.mCachedPreferenceKey = key;
        compositeDisposable = new CompositeDisposable();
        this.mApkPreferenceData = data;
        listenerQueue = new ReferenceQueue<>();
        listenerLinkedQueue = new ConcurrentLinkedQueue<>();
        if(mCachedPreferenceKey != null){
            mData = new BaseCacheData<>();
        }
    }

    public boolean isPaging(){
        return mIsPaging;
    }

    protected void savaDataToPreference(T data){
        mData.data = data;
        mData.updateTimeInMills = System.currentTimeMillis();
        Log.e("CachedData",mData.updateTimeInMills+"");
        //存储数据于SharedPreference
        BaseDataPreferenceUtil.getInstance().setString(mCachedPreferenceKey,GsonUtil.toJson(data));
        BaseDataPreferenceUtil.getInstance().setLong(mCachedPreferenceKey,mData.updateTimeInMills);
    }

    public void getCachedDataAndLoad(){
        if(mCachedPreferenceKey != null){

            //获取缓存数据
            String data = BaseDataPreferenceUtil.getInstance().getString(mCachedPreferenceKey);

            if(data != null && !TextUtils.isEmpty(data)){
                Log.e("CachedData",data);
                try {
                    //转化为对象
                    T saveData = GsonUtil.fromLocalJson(data,getTClass());
                    if(saveData != null){
                        Log.e("CachedData",saveData.toString());
                        if(mIsPaging){
                            loadSuccess(saveData,new PagingResult(false,true,true));
                        }else {
                            loadSuccess(saveData);
                        }
                        if(isNeedToUpdate()){
                            load();
                        }
                        return;
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                loadFail("没有缓存数据",new PagingResult(true,true,mIsPaging));
                load();
                return;
            }
            if(mApkPreferenceData != null){
                loadSuccess((T) GsonUtil.fromLocalJson(mApkPreferenceData,getTClass()));
            }
        }
        load();
    }


    protected void loadSuccess(T data,PagingResult... pagingResults){
        if(mIsPaging && (pagingResults == null || pagingResults.length < 1)){
            Log.e(TAG,"Page model must have paging result parameter");
            return;
        }
        Log.e("Banner","loadSuccess");
        synchronized (this){
            for(WeakReference<IBaseModelListener> weakListener:listenerLinkedQueue){
                IBaseModelListener item = weakListener.get();
                if(item != null){
                    if(pagingResults != null && pagingResults.length > 0){
                        item.loadFinish(this,data,pagingResults);
                        if(mCachedPreferenceKey != null && pagingResults[0].isFirst()){
                            savaDataToPreference(data);
                        }
                    }else {
                        item.loadFinish(this,data);
                        if(mCachedPreferenceKey != null){
                            savaDataToPreference(data);
                        }
                    }
                }
            }
        }
    }

    protected void loadFail(final String errorMsg,PagingResult... results){
        if(mIsPaging && (results == null || results.length < 1)){
            return;
        }
        synchronized (this){
            for(WeakReference<IBaseModelListener> weakListener:listenerLinkedQueue){
                if(weakListener.get() instanceof IBaseModelListener){
                    IBaseModelListener item = weakListener.get();
                    if(item != null){
                            item.loadFail(this,errorMsg,results);

                    }
                }
            }
        }
    }


    /*
    是否更新数据，这里可以设计策略，可以是一天一次，一月一次等等
    默认每次请求都更新
     */
    protected boolean isNeedToUpdate(){
        return true;
    }

    public void cancel(){
        if(compositeDisposable != null && !compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
    }

    public abstract void refresh();

    protected abstract void load();

    //缓存的数据类型
    protected Type getTClass(){
        return null;
    }

    public void register(IBaseModelListener listener){
        if(listener == null){
            return;
        }

        synchronized (this){
            //每次注册时清除被系统回收的对象
            Reference<? extends IBaseModelListener> releaseListener = null;
            while ((releaseListener = listenerQueue.poll()) != null){
                Log.e("Bannner","remove listener");
                listenerLinkedQueue.remove(releaseListener);
            }
            for(WeakReference<IBaseModelListener> weakListenter:listenerLinkedQueue){
                IBaseModelListener mListener = weakListenter.get();
                if(mListener == listener){
                    return;
                }
            }
            WeakReference<IBaseModelListener> weakListener = new WeakReference<>(listener,listenerQueue);
            listenerLinkedQueue.add(weakListener);
        }
    }

    public void unRegister(IBaseModelListener listener){
        if (listener == null){
            return;
        }

        synchronized (this){
            for(WeakReference<IBaseModelListener> weakListener:listenerLinkedQueue){
                IBaseModelListener item = weakListener.get();
                if(item == listener){
                    listenerLinkedQueue.remove(listener);
                    break;
                }
            }
        }
    }

}
