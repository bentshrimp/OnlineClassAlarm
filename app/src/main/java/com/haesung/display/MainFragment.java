package com.haesung.display;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
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

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        alarmTimeTextView = viewGroup.findViewById(R.id.alarm_time);

        sharedPref = context.getSharedPreferences("school", Context.MODE_PRIVATE);

        String alarmTime = sharedPref.getString("alarm_time","");
        assert alarmTime != null;
        if (alarmTime.getBytes().length<=0){
            alarmTimeTextView.setText("알람이 없음");
        }else{
            alarmTimeTextView.setText("알람시각. "+alarmTime);

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
            case R.id.loginButton:
                Intent intent = new Intent(context, AlarmLoginActivity.class);
                startActivity(intent);
                break;

            case R.id.unregisterButton:
                if (Objects.equals(sharedPref.getString("alarm_time", ""), "")){
                    Toast.makeText(context, "설정된 알람이 없습니다.", Toast.LENGTH_SHORT).show();
                }else{
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

        sharedPref.edit().putString("alarm_time", "").apply();

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        alarmTimeTextView.setText("알람이 없음");
        Toast.makeText(getContext(), "알람이 해지되었습니다.", Toast.LENGTH_SHORT).show();
    }
}