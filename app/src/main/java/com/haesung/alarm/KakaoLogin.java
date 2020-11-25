package com.haesung.alarm;

import android.webkit.WebView;

public class KakaoLogin extends Login {

    public KakaoLogin(WebView webView, String schoolUrl, String loginPageUrl, String id, String pw, String classUrl, String comment) {
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
                break;

            case 2:
                clickId("lg_sns03");
                sleep();
                inputIdValue("id_email_2",id);
                sleep();
                inputIdValue("id_password_3", pw);
                sleep();
                clickClass("btn_g btn_confirm submit");
                break;

            case 3:
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
