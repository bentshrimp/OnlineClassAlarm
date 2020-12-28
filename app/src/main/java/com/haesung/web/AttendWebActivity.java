package com.haesung.web;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.haesung.alarm.R;

public class AttendWebActivity extends AppCompatActivity{

    WebView webView;

    String classUrl;
    String comment;

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        SharedPreferences sharedPref = getSharedPreferences("school", Context.MODE_PRIVATE);
        classUrl = sharedPref.getString("attend_url","");
        comment = sharedPref.getString("comment","");

        initWebView();
        webView.loadUrl(classUrl);

    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView(){
        webView = findViewById(R.id.webView);
        webView.canGoBack();
        webView.canGoForward();
        webView.setWebViewClient(new MyWebClient());
        webView.setWebChromeClient(new MyWebChromeClient());

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

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
    }

    public class MyWebChromeClient extends WebChromeClient{
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