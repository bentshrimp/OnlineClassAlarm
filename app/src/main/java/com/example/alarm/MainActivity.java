package com.example.alarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // drawer layout 변수
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    // 알람 시간
    private Calendar calendar;
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*drawer layout*/
        //Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //Tool bar
        setSupportActionBar(toolbar);

        //Navigation Drawer menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_alarm);

        /*alarm*/
        this.calendar = Calendar.getInstance();
        // 현재 날짜 표시
        displayDate();

        this.timePicker = findViewById(R.id.timePicker);

        findViewById(R.id.btnRegist).setOnClickListener(mClickListener);
        findViewById(R.id.btnUnregist).setOnClickListener(mClickListener);
    }

    /*뒤로 가기 버튼으로 드로어 네비게이션 닫기*/
    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    /*현재 네비게이션 메뉴 선택*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        switch (menuItem.getItemId()){
            case R.id.nav_alarm:
                break;

            case R.id.nav_setting:
                Intent intent = new Intent(MainActivity.this, Setting.class);
                startActivity(intent);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /* 날짜 표시 */
    private void displayDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        ((TextView) findViewById(R.id.txtDate)).setText(format.format(this.calendar.getTime()));
    }

    /* 알람 등록 */

    private void Regist() {
        // 알람 시간 설정
        this.calendar.set(Calendar.HOUR_OF_DAY, this.timePicker.getHour());
        this.calendar.set(Calendar.MINUTE, this.timePicker.getMinute());
        this.calendar.set(Calendar.SECOND, 0);

        // Receiver 설정
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 알람 설정
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, this.calendar.getTimeInMillis(), pendingIntent);

        // Toast 보여주기 (알람 시간 표시)
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Toast.makeText(this, "Alarm : " + format.format(calendar.getTime()), Toast.LENGTH_LONG).show();
    }


    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btnRegist:
                    // 알람 등록
                    Regist();
                    break;


            }
        }
    };

}