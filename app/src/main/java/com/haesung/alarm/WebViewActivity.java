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
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity{

    WebView webView;

    String schoolUrl;
    String loginPageUrl;
    String classUrl;

    String id;
    String pw;
    String loginWay;
    String comment;

    Login login;

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        SharedPreferences sharedPref = getSharedPreferences("school", Context.MODE_PRIVATE);
        schoolUrl = sharedPref.getString("my_school","");
        loginPageUrl = sharedPref.getString("login_page","");
        id = sharedPref.getString("id","");
        pw = sharedPref.getString("password","");
        loginWay = sharedPref.getString("login_way","");
        classUrl = sharedPref.getString("attend_url","");
        comment = sharedPref.getString("comment","");
        selectLoginWay();
        initWebView();
    }

    public void selectLoginWay(){

        switch (loginWay) {
            case "ebs":
                login = new EbsLogin(webView, schoolUrl, loginPageUrl, id, pw, classUrl, comment);
                break;

            case "kakao":
                login = new KakaoLogin(webView, schoolUrl, loginPageUrl, id, pw, classUrl, comment);
                break;

            default:

        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    public void initWebView() {
        webView = findViewById(R.id.webView);
        webView.clearHistory();
        webView.clearCache(true);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webView.setWebViewClient(new myWebClient());
        webView.setWebChromeClient(new myWebChromeClient());

        webView.loadUrl(schoolUrl);
    }

    public class myWebClient extends WebViewClient {

        int num=0;

        int errorCnt=0;

        @Override
        public void onPageFinished(WebView view, String url) {
            sleep();
            login.attend(num);
            num++;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            if(errorCnt<=3){
                login.attend(--num);
                errorCnt++;
            }
            super.onReceivedError(view, request, error);
        }

        public void sleep(){
            try {
                Thread.sleep(300);
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