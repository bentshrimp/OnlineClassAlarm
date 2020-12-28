package com.haesung.web;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.haesung.alarm.R;

public class SettingWebViewActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_web_view);

        initWebView();
        webView.loadUrl("https://oc.ebssw.kr/");    // EBS온라인클래스 접속

        final Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            int processNum = 0;
            @Override
            public void onClick(View view) {
                @SuppressLint("CommitPrefEdits")
                SharedPreferences.Editor editor = getSharedPreferences("school", Context.MODE_PRIVATE).edit();

                switch (processNum){
                    case 0 :
                        editor.putString("my_school",webView.getUrl());         // 우리 학교 온라인클래스 주소 저장
                        editor.apply();
                        Toast.makeText(SettingWebViewActivity.this, "학교 정보 저장됨 "+processNum, Toast.LENGTH_SHORT).show();
                        processNum++;                   // processNum 1증가
                        saveButton.setText("출석부 목록에서 클릭");
                        break;

                    case 1:
                        editor.putString("attend_url", webView.getUrl());       // 우리 반 클래스 출석부 주소 저장
                        editor.apply();
                        processNum++;                   // processNum 1증가
                        Toast.makeText(SettingWebViewActivity.this, "출석부 정보 저장됨 "+processNum, Toast.LENGTH_SHORT).show();
                        CookieManager.getInstance().flush();
                        break;
                }
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView(){
        webView = findViewById(R.id.settingWebView);
        webView.clearHistory();
        webView.clearCache(true);
        webView.clearFormData();
        CookieManager.getInstance().removeAllCookies(null);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        }
    }