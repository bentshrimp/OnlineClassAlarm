package com.haesung.alarm;

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

public class WebViewActivity extends AppCompatActivity{

    WebView webView;

    String attendUrl;
    String comment;

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        SharedPreferences sharedPref = getSharedPreferences("school", Context.MODE_PRIVATE);
        attendUrl = sharedPref.getString("attend_url","");
        comment = sharedPref.getString("comment","");

        initWebView();

        webView.loadUrl(attendUrl);

    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    public void initWebView(){
        webView = findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webView.setWebViewClient(new myWebClient());
        webView.setWebChromeClient(new myWebChromeClient());
    }

    public class myWebClient extends WebViewClient {
        int processNum = 0;
        @Override
        public void onPageFinished(WebView view, String url) {
            sleep(500);
            switch (processNum){
                case 0:
                    view.loadUrl("javascript:document.getElementsByClassName('class_nm_ellipsis')[0].click()");
                    processNum++;
                    break;
                case 1:
                    view.loadUrl("javascript:function afterLoad() {"
                            + "document.getElementsByName('cmmntsCn')[0].value = '"+comment+"';"
                            + "};"
                            + "afterLoad();");
                    sleep(100);
                    view.loadUrl("javascript:fn_commentSave(null,'','369307');");
                    processNum++;
                    break;
                default:
            }
        }

        public void sleep(int millis){
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class myWebChromeClient extends WebChromeClient {
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