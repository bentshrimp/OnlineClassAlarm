package com.haesung.display;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.haesung.alarm.AlarmReceiver;
import com.haesung.alarm.R;
import com.haesung.web.SelfCheckActivity;

import java.util.Objects;

import static android.content.Context.ALARM_SERVICE;

public class MainFragment extends Fragment implements View.OnClickListener{

    TextView alarmTimeTextView;

    SharedPreferences sharedPref;

    Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        alarmTimeTextView = viewGroup.findViewById(R.id.alarm_time);

        sharedPref = context.getSharedPreferences("school", Context.MODE_PRIVATE);

        String alarmTime = sharedPref.getString("alarm_time","");   // SettingFragment에서 저장한 시간을 보여줌
        assert alarmTime != null;
        if (alarmTime.getBytes().length<=0){            //  alarmTime 의 길이가 0이하 => 저장된 알람 시간이 없음
            alarmTimeTextView.setText("No Alarm");
        }else{                                          // 알람 시간이 저장됨
            alarmTimeTextView.setText(alarmTime);

        }

        Button loginButton = viewGroup.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

        Button unRegisterButton = viewGroup.findViewById(R.id.unregisterButton);
        unRegisterButton.setOnClickListener(this);

        Button selfCheckButton = viewGroup.findViewById(R.id.selfCheckButton);
        selfCheckButton.setOnClickListener(this);
        return viewGroup;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginButton:                                              // 로그인 버튼을 누르면
                Intent intent = new Intent(context, AlarmLoginActivity.class);  // 온라인클래스 로그인 창으로 이동
                startActivity(intent);
                break;

            case R.id.unregisterButton:                                         // 알람 해지 버튼을 누르면
                if (Objects.equals(sharedPref.getString("alarm_time", ""), "")){ // 이전에 설정된 알람이 없었다면
                    Toast.makeText(context, "설정된 알람이 없습니다.", Toast.LENGTH_SHORT).show();
                }else{                                                                      // 이전에 설정된 알람이 있었다면
                    alarmUnRegister();
                }
                break;

            case R.id.selfCheckButton:
                Intent intent1 = new Intent(context, SelfCheckActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @SuppressLint("CommitPrefEdits")
    public void alarmUnRegister() {
        Intent intent = new Intent (context.getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        sharedPref.edit().putString("alarm_time", "").apply();  // 알람 시간을 지움

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);                            // 알람 취소

        alarmTimeTextView.setText("No Alarm");                       // 알람 시간 대신 "알람이 없음"을 표시
        Toast.makeText(getContext(), "알람이 해지되었습니다.", Toast.LENGTH_SHORT).show();
    }
}