package com.example.administrator.wanandroid.utils;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.example.administrator.wanandroid.MyApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {
    private static final String COOKIE_PREF = "cookies_prefs";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        String cookie = getCookie(request.url().toString(), request.url().host());
//        Log.e("AddCookie", cookie);
        if (!TextUtils.isEmpty(cookie)) {
            builder.addHeader("Cookie", cookie);
//            Log.e("AddCookie", "cookie = " + cookie);
        }
        return chain.proceed(builder.build());
    }
    private String getCookie(String url, String domain) {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
        if (!TextUtils.isEmpty(url) && sp.contains(url) && !TextUtils.isEmpty(sp.getString(url, ""))) {
            return sp.getString(url, "");
        }
        if (!TextUtils.isEmpty(domain) && sp.contains(domain) &&
                !TextUtils.isEmpty(sp.getString(domain, ""))) {
//            Log.e("AddCookie", "domain = " + domain);
            return sp.getString(domain, "");
        }
        return null;
    }
}
