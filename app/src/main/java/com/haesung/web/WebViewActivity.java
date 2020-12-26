package com.haesung.web;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.haesung.alarm.R;

public class WebViewActivity extends AppCompatActivity{

    WebView webView;

    String schoolUrl;
    String classUrl;
    String comment;

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        SharedPreferences sharedPref = getSharedPreferences("school", Context.MODE_PRIVATE);
        schoolUrl = sharedPref.getString("my_school","");
        classUrl = sharedPref.getString("attend_url","");
        comment = sharedPref.getString("comment","");

        initWebView();
        webView.loadUrl(classUrl);

    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView(){
        webView = findViewById(R.id.webView);

        webView.canGoForward();
        webView.canGoBack();
        webView.setWebViewClient(new MyWebClient());
        webView.setWebChromeClient(new MyWebChromeClient());

        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportMultipleWindows(true);
    }

    public class MyWebClient extends WebViewClient {
        int i=0;
        @Override
        public void onPageFinished(WebView view, String url) {
            if(i==0){
                webView.loadUrl("javascript:document.getElementsByClassName('class_nm_ellipsis')[0].click()");
                i++;
            }else if(i==1){
                sleep();
                webView.loadUrl("javascript:function afterLoad() {"
                        + "document.getElementsByName('cmmntsCn')[0].value = '"+comment+"';"
                        + "};"
                        + "afterLoad();");
                sleep();
                webView.loadUrl("javascript:document.getElementsByClassName('submit')[0].click()");
                i++;
            }
        }

        public void sleep(){
            try {
                Thread.sleep(200);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Toast.makeText(getApplicationContext(), "로그인이 되지 않음", Toast.LENGTH_SHORT).show();
            finish();
        }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
    }

    public static class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            result.confirm();
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            result.confirm();
            return true;
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            result.confirm();
            return true;
        }
    }
}