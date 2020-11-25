package com.haesung.alarm;


import android.webkit.WebView;

public class EbsLogin extends Login {
    String schoolUrl;
    String loginPageUrl;
    String id;
    String pw;
    String classUrl;
    String comment;

    WebView webView;

    public EbsLogin(WebView webView, String schoolUrl, String loginPageUrl, String id, String pw, String classUrl, String comment) {
        super(webView, schoolUrl, loginPageUrl, id, pw, classUrl, comment);
    }

    @Override
    public void attend(int loadNum) {
        switch (loadNum){
            case 0:
                webView.loadUrl(schoolUrl);
                break;

            case 1:
                webView.loadUrl(loginPageUrl);
                sleep();
                inputIdValue("j_username", id);
                sleep();
                inputIdValue("j_password", pw);
                sleep();
                clickClass("img_type");
                break;

            case 2:
                webView.loadUrl(classUrl);
                sleep();
                webView.loadUrl("javascript:document.getElementsByClassName('class_nm_ellipsis')[0].click()");
                sleep();
                webView.loadUrl("javascript:function afterLoad() {"
                        + "document.getElementsByName('cmmntsCn')[0].value = '"+comment+"';"
                        + "};"
                        + "afterLoad();");
                sleep();
                clickClass("submit");
                break;
        }
    }
}
