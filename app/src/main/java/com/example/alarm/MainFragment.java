package com.example.alarm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

public class MainFragment extends Fragment {

    TextView alarmTimeTextView;

    Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        alarmTimeTextView = viewGroup.findViewById(R.id.alarm_time);

        final SharedPreferences sharedPref = context.getSharedPreferences("school", Context.MODE_PRIVATE);

        String alarmTime = sharedPref.getString("alarm_time","");
        assert alarmTime != null;
        if (alarmTime.getBytes().length<=0){
            alarmTimeTextView.setText("알람이 없음");
        }else{
            alarmTimeTextView.setText("알람시각. "+alarmTime);

        }

        Button loginButton = viewGroup.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LoginWebViewActivity.class);
                startActivity(intent);
            }
        });

        return viewGroup;
    }


}