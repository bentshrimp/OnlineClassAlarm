package com.example.alarm;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import static android.app.AlarmManager.INTERVAL_DAY;
import static android.content.Context.ALARM_SERVICE;

public class SettingAlarmFragment extends Fragment {

    // 알람 시간
    private Calendar calendar;
    private TimePicker timePicker;
    private PendingIntent alarmIntent;
    private AlarmManager alarmManager;

    Context context;

    SharedPreferences sharedPrefer;
    SharedPreferences.Editor editor;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_setting_alarm, container, false);
        
        /*alarm*/
        calendar = Calendar.getInstance();

        // 현재 날짜 표시
        displayDate(viewGroup);

        this.timePicker = viewGroup.findViewById(R.id.timePicker);

        editor = context.getSharedPreferences("school", Context.MODE_PRIVATE).edit();
        sharedPrefer = context.getSharedPreferences("school", Context.MODE_PRIVATE);

        Button registerButton = viewGroup.findViewById(R.id.btnRegist);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        Button unRegisterButton = viewGroup.findViewById(R.id.btnUnregist);
        unRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sharedPrefer.getString("alarm_time", "").equals("")){
                    Toast.makeText(context, "해지 할 알람이 없음", Toast.LENGTH_SHORT).show();
                }else{
                    unRegister();
                }

            }
        });
        return viewGroup;
    }

    /* 날짜 표시 */
    private void displayDate(ViewGroup viewGroup) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        ((TextView) viewGroup.findViewById(R.id.txtDate)).setText(format.format(calendar.getTime()));
    }

    /* 알람 등록 */

    private void register() {

        // 알람 시간 설정
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, this.timePicker.getHour());
        calendar.set(Calendar.MINUTE, this.timePicker.getMinute());
        calendar.set(Calendar.SECOND, 0);

        editor.putString("alarm_time", timePicker.getHour()+"시 "+timePicker.getMinute()+"분");
        editor.apply();

        // Receiver 설정
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 알람 설정
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (calendar.before(Calendar.getInstance())){
            calendar.add(Calendar.DATE, 1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
            else {
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), INTERVAL_DAY, alarmIntent);
            }
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
            else {
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), INTERVAL_DAY, alarmIntent);
            }
        }

        // Toast 보여주기 (알람 시간 표시)
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Toast.makeText(context, "Alarm : " + format.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
    }

    private void unRegister() {
        Intent intent = new Intent (context.getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        editor.putString("alarm_time", "");
        editor.apply();

        alarmManager.cancel(pendingIntent);
        //Toast 보여주기
        Toast.makeText(getContext(), "알람이 해지되었습니다.", Toast.LENGTH_SHORT).show();
    }

    
    
}