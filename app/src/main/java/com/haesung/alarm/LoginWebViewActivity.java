package com.haesung.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.util.Objects;

public class LoginWebViewActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_web_view_acitivty);

        SharedPreferences sharedPref = getSharedPreferences("school", MODE_PRIVATE);

        initWebView();

        webView.loadUrl(Objects.requireNonNull(sharedPref.getString("mySchool", "")));
        webView.loadUrl(Objects.requireNonNull(sharedPref.getString("myLogin", "")));

        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CookieManager.getInstance().flush();
                finish();
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView(){
        webView = findViewById(R.id.loginWebView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webView.setWebViewClient(new myWebClient());
        webView.setWebChromeClient(new myWebChromeClient());

    }

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            CookieManager.getInstance().flush();
            sleep(200);
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