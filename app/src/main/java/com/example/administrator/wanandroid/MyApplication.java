package com.example.administrator.wanandroid;

import android.app.Application;
import android.content.Context;

import com.example.administrator.wanandroid.viewstatus.LoadErrorCallBack;
import com.example.administrator.wanandroid.viewstatus.LoadingCallback;
import com.example.administrator.wanandroid.viewstatus.NetworkErrorCallback;
import com.kingja.loadsir.core.LoadSir;

public class MyApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LoadSir.beginBuilder()
                .addCallback(new LoadingCallback())
                .addCallback(new LoadErrorCallBack())
                .addCallback(new NetworkErrorCallback())
                .setDefaultCallback(LoadingCallback.class)
                .commit();
        //Log.d("Application",context.toString());
    }
    public static Context getContext(){
            return context;
    }


}
