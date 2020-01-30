package com.example.administrator.wanandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.administrator.wanandroid.MyApplication;

public class BaseDataPreferenceUtil {

    private static BaseDataPreferenceUtil instance = null;

    public static BaseDataPreferenceUtil getInstance(){
        if(instance == null){
            instance = new BaseDataPreferenceUtil();
        }
        return instance;
    }

    public void setString(String key,String data){
        SharedPreferences pref = MyApplication.getContext().getSharedPreferences(key,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("data",data);
        editor.apply();
    }

    public void setLong(String key,long data){
        SharedPreferences pref = MyApplication.getContext().getSharedPreferences(key,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong("time",data);
        editor.apply();
    }

    public String getString(String key){
        SharedPreferences pref = MyApplication.getContext().getSharedPreferences(key, Context.MODE_PRIVATE);
        return pref.getString("data",null);
    }

    public long getLong(String key){
        SharedPreferences pref = MyApplication.getContext().getSharedPreferences(key, Context.MODE_PRIVATE);
        return pref.getLong("time",0);
    }

    public void saveLoginStatus(String username){
        if(username == null){
            Log.e("saveLoginStatus","null");
        }else {
            Log.e("saveLoginStatus",username);
        }

        SharedPreferences pref = MyApplication.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name",username);
        editor.apply();
    }

    public String getLoginStatus(){
        SharedPreferences pref = MyApplication.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        return pref.getString("name",null);
    }

}
