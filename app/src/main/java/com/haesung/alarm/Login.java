package com.haesung.alarm;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public abstract class Login {
    String schoolUrl;
    String loginPageUrl;
    String id;
    String pw;
    String classUrl;
    String comment;

    WebView webView;

    public Login(WebView webView, String schoolUrl, String loginPageUrl, String id, String pw, String classUrl, String comment) {
        this.webView = webView;
        this.schoolUrl = schoolUrl;
        this.loginPageUrl = loginPageUrl;
        this.id = id;
        this.pw = pw;
        this.classUrl = classUrl;
        this.comment = comment;
    }

    public abstract void attend(int loadNum);

    public void loadJavascript(String javascript){
        webView.loadUrl("javascript:"+javascript);
    }

    public void inputIdValue(String key, String value){
        webView.loadUrl("javascript:function afterLoad() {"
                + "document.getElementsById('"+key+"').value = '"+value+"';"
                + "};"
                + "afterLoad();");
    }

    public void inputClassValue(String key, String value){
        webView.loadUrl("javascript:function afterLoad() {"
                + "document.getElementsByName('"+key+"')[0].value = '"+value+"';"
                + "};"
                + "afterLoad();");
    }

    public void clickId(String key){
        webView.loadUrl("javascript:document.getElementsById('"+key+"').click()");
    }

    public void clickClass(String key){
        webView.loadUrl("javascript:document.getElementsByClassName('"+key+"')[0].click()");
    }

    public void sleep(){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
