package com.haesung.web;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.haesung.alarm.R;

public class LoginWebViewActivity extends AppCompatActivity {

    WebView webView;

    String schoolUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_web_view);

        SharedPreferences sharedPref = getSharedPreferences("school", Context.MODE_PRIVATE);
        schoolUrl = sharedPref.getString("my_school","");

        initWebView();
        webView.loadUrl(schoolUrl);

        Button button = findViewById(R.id.exitButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CookieManager.getInstance().flush();
                finish();
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView(){
        webView = findViewById(R.id.webView);
        webView.canGoBack();
        webView.canGoForward();
        webView.setWebViewClient(new MyWebClient());
        webView.setWebChromeClient(new MyWebChromeClient());

        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
    }

    public class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            CookieManager.getInstance().flush();
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