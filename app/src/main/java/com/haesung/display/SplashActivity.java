package com.haesung.display;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.haesung.alarm.R;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_TIME = 700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                @Override
                public void run(){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }, SPLASH_DISPLAY_TIME);
        } catch (RuntimeException e) {
            e.printStackTrace();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        /* 스플래시 화면에서 뒤로가기 기능 제거. */
    }

}
