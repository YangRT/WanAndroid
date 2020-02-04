package com.example.administrator.wanandroid;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.security.KeyChain;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.collect.CollectHelper;
import com.example.administrator.wanandroid.databinding.ActivityArticleBinding;
import com.example.administrator.wanandroid.viewstatus.LoadingCallback;
import com.example.administrator.wanandroid.viewstatus.NetworkErrorCallback;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebSettingsImpl;
import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import java.security.Key;

public class ArticleActivity extends AppCompatActivity implements View.OnClickListener, CollectHelper.CollectCallBackListener {

    private ActivityArticleBinding binding;
    private CollectHelper helper;
    private boolean isCollect;
    private int id;
    private int originId;
    private LoadSir loadSir;
    private LoadService loadService;
    private AgentWeb agentWeb;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_article);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorTheme));
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
        isCollect = intent.getBooleanExtra("collect",false);
        originId = intent.getIntExtra("originId",-1);
        id = intent.getIntExtra("id",0);
        Log.e("ArticleActivity",""+id+" "+isCollect+" "+url);
        binding.articleTitle.setText(title);
        binding.articleBack.setOnClickListener(this);
        binding.articleCollect.setOnClickListener(this);
        if(isCollect){
            binding.articleCollect.setImageResource(R.drawable.favorite);
        }else {
            binding.articleCollect.setImageResource(R.drawable.un_favorite);
        }

        loadSir = new LoadSir.Builder()
                .addCallback(new NetworkErrorCallback())
                .addCallback(new LoadingCallback())
                .setDefaultCallback(LoadingCallback.class).build();
        loadService = loadSir.register(binding.articleWebView, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                loadService.showCallback(LoadingCallback.class);
                agentWeb.getWebCreator().getWebView().reload();
            }
        });
        Log.e("ArticleActivity",title);
        helper = new CollectHelper();
        helper.setCollectCallBackListener(this);
        WebChromeClient webChromeClient = new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                binding.articleTitle.setText(title);
            }
        };
        WebViewClient webViewClient = new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(error.getErrorCode() >= 400)
                        loadService.showCallback(NetworkErrorCallback.class);
                }else {
                    loadService.showCallback(NetworkErrorCallback.class);
                }
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                loadService.showSuccess();
            }

            @Override
            public void onPageFinished(WebView view, String url) {

            }
        };
        if(url!=null){
            try{
                agentWeb = AgentWeb.with(this)
                        .setAgentWebParent(binding.articleWebView,new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT) )
                        .useDefaultIndicator()
                        .setWebChromeClient(webChromeClient)
                        .setWebViewClient(webViewClient)
                        .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                        .createAgentWeb()
                        .ready()
                        .go(url);
                agentWeb.getWebCreator().getWebView().getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                agentWeb.getWebCreator().getWebView().getSettings().setJavaScriptEnabled(true);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

//    private void initWebView(){
//
//        mWebView.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                handler.proceed();
//            }
//
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                super.onReceivedError(view, request, error);
//                loadService.showCallback(NetworkErrorCallback.class);
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                mWebView.loadUrl(request.getUrl().toString());
//                return true;
//            }
//
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                loadService.showCallback(LoadingCallback.class);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                loadService.showSuccess();
//            }
//        });
//        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
//
//        mWebView.setWebChromeClient(new WebChromeClient(){
//
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                binding.articleTitle.setText(title);
//            }
//
//            @Override
//            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
//                return true;
//            }
//
//            @Override
//            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
//                return true;
//            }
//
//            @Override
//            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//                return true;
//            }
//
//            @Override
//            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
//                return true;
//            }
//        });
//        //设置自适应屏幕，两者合用
//        mWebView.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
//        //mWebView.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        mWebView.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
//        mWebView.getSettings().setDomStorageEnabled(true);
//        mWebView.getSettings().setDatabaseEnabled(true);
//        mWebView.getSettings().setAppCacheEnabled(true);
//        mWebView.getSettings().setAppCachePath(getFilesDir().getAbsolutePath()+"/webcache");
//        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.getSettings().setBlockNetworkImage(true);
//        mWebView.getSettings().setAllowFileAccess(true);
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.article_back:
                if(agentWeb != null && !agentWeb.back()){
                    finish();
                }else {
                    agentWeb.getWebCreator().getWebView().goBack();
                }

                break;
            case R.id.article_collect:
                if(isCollect){
                    if(originId > 0){
                        helper.unCollectArticle(id,originId,-1);
                    }else {
                        helper.unCollectArticle(id,-1);
                    }

                }else {
                    helper.addCollectArticle(id,-1);
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (agentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if (!agentWeb.back()){
                finish();
            }else {
                agentWeb.getWebCreator().getWebView().goBack();
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCollectSuccess(int position) {
        Toast.makeText(this,"收藏成功！",Toast.LENGTH_SHORT).show();
        binding.articleCollect.setImageResource(R.drawable.favorite);
    }

    @Override
    public void onUnCollectSuccess(int position) {
        Toast.makeText(this,"取消收藏成功！",Toast.LENGTH_SHORT).show();
        binding.articleCollect.setImageResource(R.drawable.un_favorite);
    }

    @Override
    public void onFail(String message, int type) {
        if(type == 0){
            Toast.makeText(this,"收藏失败！",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"取消收藏失败！",Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onResume() {
        agentWeb.getWebLifeCycle().onResume();
       super.onResume();
    }

    @Override
    protected void onPause() {
        agentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }




    @Override
    protected void onDestroy() {
        agentWeb.getWebLifeCycle().onDestroy();

        super.onDestroy();
    }

}
