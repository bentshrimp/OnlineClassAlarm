package com.haesung.setting;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.haesung.alarm.AlarmReceiver;
import com.haesung.alarm.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.app.AlarmManager.INTERVAL_DAY;
import static android.content.Context.ALARM_SERVICE;
import static android.widget.Toast.LENGTH_SHORT;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    Context context;

    EditText comment;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    private Calendar calendar;
    private TimePicker timePicker;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_settings, container, false);

        comment = viewGroup.findViewById(R.id.comment);
        timePicker = viewGroup.findViewById(R.id.timePicker);
        calendar = Calendar.getInstance();

        sharedPref = context.getSharedPreferences("school", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        comment.setText(sharedPref.getString("comment",""));

        Button webViewButton = viewGroup.findViewById(R.id.webView_button);
        webViewButton.setOnClickListener(this);

        Button saveButton = viewGroup.findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);

        return viewGroup;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.webView_button:               //webView_button 을 누르면
                Intent intent = new Intent(context, SettingWebViewActivity.class); //SettingWebViewActivity 를 보여준다
                startActivity(intent);
                break;

            case R.id.save_button:
                String commentStr = "";

                try {
                    commentStr = comment.getText().toString();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }

                String schoolUrl = sharedPref.getString("my_school","");
                String attendUrl = sharedPref.getString("attend_url","");

                if(schoolUrl.getBytes().length<=0){             // 저장된 학교 정보가 없다면
                    Toast.makeText(context, "학교 정보가 저장되지 않았습니다.", LENGTH_SHORT).show();
                }
                else if(attendUrl.getBytes().length<=0){        // 저장된 출석부 정보가 없다면
                    Toast.makeText(context, "출석부 정보가 저장되지 않았습니다.", LENGTH_SHORT).show();
                }
                else if(commentStr.getBytes().length<=0){       // 저장된 출석 문구가 없다면
                    Toast.makeText(context, "출석 문구가 저장되지 않았습니다.", LENGTH_SHORT).show();
                }
                else{
                    editor.putString("comment",commentStr);
                    alarmRegister();                            // 설정이 완료되었다면
                    Toast.makeText(context, "설정이 완료되었습니다.", LENGTH_SHORT).show();
                }
        }
    }

    public void alarmRegister() {

        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, this.timePicker.getHour());  // timePicker에서 설정한 시간의 시를 가져옴
        calendar.set(Calendar.MINUTE, this.timePicker.getMinute());     // timePicker에서 설정한 시간의 분을 가져옴
        calendar.set(Calendar.SECOND, 0);                               // 초는 0으로

        editor.putString("alarm_time", timePicker.getHour()+"시 "+timePicker.getMinute()+"분");
        editor.apply();                                                 // 설정한 시간을 ~시 ~분으로 저장

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (calendar.before(Calendar.getInstance())){                   // 설정한 시간이 현재보다 이전이라면
            calendar.add(Calendar.DATE, 1);                         //  내일 이 시간으로
        /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
            else {*/
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), INTERVAL_DAY, pendingIntent);
            /*}*/
        }
        else{
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }*/
            /* else {*/
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), INTERVAL_DAY, pendingIntent);
            /* }*/
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Toast.makeText(context, "Alarm : " + format.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
    }

   /*private boolean isEmptyStrings(String... params){
        for (String param : params){
            if (param.getBytes().length<=0){
                Toast.makeText(context, "설정이 완벽하지 않습니다", LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }*/
}