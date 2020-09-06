package com.example.alarm;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class SettingWebViewActivity extends AppCompatActivity {

    WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_web_view);

        webView = findViewById(R.id.settingWebView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webView.setWebViewClient(new myWebClient());
        webView.setWebChromeClient(new myWebChromeClient());

        webView.loadUrl("https://oc.ebssw.kr/");

        Button storeButton = findViewById(R.id.storeButton);
        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CookieManager.getInstance().flush();

                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = getSharedPreferences("school", Context.MODE_PRIVATE).edit();
                editor.putString("attend_url", webView.getUrl());
                editor.apply();

                Toast.makeText(SettingWebViewActivity.this, "정보 저장됨", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }



    public class myWebClient extends WebViewClient {
        int processNum = -1;
        SharedPreferences.Editor editor = getSharedPreferences("school", Context.MODE_PRIVATE).edit();

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            processNum++;
            switch (processNum){
                case 1 :
                    editor.putString("mySchool",url);
                    editor.apply();
                    break;

                case 2:
                    editor.putString("myLogin",url);
                    editor.apply();
                    break;
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