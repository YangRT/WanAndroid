package com.example.administrator.wanandroid.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.example.administrator.wanandroid.MyApplication;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SaveCookiesInterceptor implements Interceptor {
    private static final String COOKIE_PREF = "cookies_prefs";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        //set-cookie可能为多个
        if (!response.headers("Set-Cookie").isEmpty()) {
            List<String> cookies = response.headers("Set-Cookie");
            String cookie = encodeCookie(cookies);
            Log.e("SaveCookie", cookie);
            saveCookie(request.url().toString(), request.url().host(), cookie);
        }
        return response;
    }

    private String encodeCookie(List<String> cookies) {
        StringBuilder sb = new StringBuilder();
        Set<String> set = new HashSet<>();
        for (String cookie : cookies) {
            String []arr = cookie.split(";");
            for (String s : arr) {
                if (set.contains(s)) {
                    continue;
                }
                set.add(s);
            }
        }

        Iterator<String> ite = set.iterator();
        while (ite.hasNext()) {
            String cookie = ite.next();
            sb.append(cookie).append(";");
        }

        int last = sb.lastIndexOf(";");
        if (sb.length() - 1 == last) {
            sb.deleteCharAt(last);
        }

        return sb.toString();
    }

    private void saveCookie(String url, String domain, String cookies) {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException("url is null");
        } else {
            editor.putString(url, cookies);
        }

        if (!TextUtils.isEmpty(domain)) {
            editor.putString(domain, cookies);
        }

        editor.apply();
    }
}
