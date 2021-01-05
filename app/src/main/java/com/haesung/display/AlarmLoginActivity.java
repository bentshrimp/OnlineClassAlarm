package com.haesung.display;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.haesung.alarm.R;
import com.haesung.web.LoginWebViewActivity;
import com.haesung.web.SelfCheckActivity;
import com.haesung.web.AttendWebActivity;

public class AlarmLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_login);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1){
            LinearLayout ll = findViewById(R.id.backGround);
            ll.setBackground(getResources().getDrawable(R.drawable.color_background2,null));
        }

        Button button = findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlarmLoginActivity.this, LoginWebViewActivity.class);
                startActivity(intent);
            }
        });

        Button button1 = findViewById(R.id.attendButton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlarmLoginActivity.this, AttendWebActivity.class);
                startActivity(intent);
            }
        });

        Button button2 = findViewById(R.id.selfCheckButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlarmLoginActivity.this, SelfCheckActivity.class);
                startActivity(intent);
            }
        });
    }
}