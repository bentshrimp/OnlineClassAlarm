package com.haesung.display;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haesung.alarm.R;



public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(this);

        if(isFirstRun()){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, new SettingsFragment()).commit();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, new MainFragment()).commit();



    }

    @SuppressLint("CommitPrefEdits")
    public boolean isFirstRun(){
        SharedPreferences sp = getSharedPreferences("run",MODE_PRIVATE);
        boolean isFirstRun = sp.getBoolean("isFirstRun",true);
        if(isFirstRun){
            sp.edit().putBoolean("isFirstRun", false).apply();
        }

        return isFirstRun;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, new MainFragment()).commit();
                break;

            case R.id.nav_setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, new SettingsFragment()).commit();
                break;
        }
        return true;
    }
}