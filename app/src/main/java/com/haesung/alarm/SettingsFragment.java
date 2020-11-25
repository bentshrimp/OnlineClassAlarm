package com.haesung.alarm;

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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class SettingsFragment extends Fragment {

    Context context;

    EditText id;
    EditText password;
    EditText comment;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_settings, container, false);

        id = viewGroup.findViewById(R.id.id);
        password = viewGroup.findViewById(R.id.password);
        comment = viewGroup.findViewById(R.id.comment);

        final RadioGroup radioGroup = viewGroup.findViewById(R.id.radioGroup);

        final SharedPreferences sharedPref = context.getSharedPreferences("school", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor editor = sharedPref.edit();

        Button webViewButton = viewGroup.findViewById(R.id.webView_button);
        webViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SettingWebViewActivity.class);
                startActivity(intent);
            }
        });

        Button saveButton = viewGroup.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String idStr = id.getText().toString();
                String passwordStr = password.getText().toString();

                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = viewGroup.findViewById(radioButtonId);
                String loginWayStr = radioButton.getText().toString();

                String attendUrlStr = sharedPref.getString("attend_url","");
                String commentStr = comment.getText().toString();



                if (isEmptyStrings(idStr, passwordStr, loginWayStr, attendUrlStr, commentStr)){
                    Toast.makeText(context, "저장실패", LENGTH_SHORT).show();
                }else{
                    editor.putString("id",idStr);
                    editor.putString("password",passwordStr);
                    editor.putString("login_way", loginWayStr);
                    editor.putString("attend_url",attendUrlStr);
                    editor.putString("comment",commentStr);
                    Toast.makeText(context, "저장성공", LENGTH_SHORT).show();
                }
            }
        });

        return viewGroup;
    }
    private boolean isEmptyStrings(String... params){
        for (String param : params){
            if (param.isEmpty()){
                return true;
            }
        }
        return false;
    }
}