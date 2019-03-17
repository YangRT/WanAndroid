package com.example.administrator.wanandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigUtil {
    private static String APP_ID = "WanAndroid";

    public static void cacheUsername(Context context,String username){
        SharedPreferences pref = context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("username",username);
        editor.apply();
    }

    public static String getUsername(Context context){
        SharedPreferences pref = context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE);
        return  pref.getString("username","");
    }
}
