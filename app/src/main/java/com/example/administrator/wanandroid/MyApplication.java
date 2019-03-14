package com.example.administrator.wanandroid;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class MyApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //Log.d("Application",context.toString());
    }
    public static Context getContext(){
            return context;
    }


}
