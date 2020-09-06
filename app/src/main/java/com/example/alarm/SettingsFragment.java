package com.example.alarm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import static android.widget.Toast.LENGTH_SHORT;

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_settings, container, false);
        final SharedPreferences sharedPref = getActivity().getSharedPreferences("school", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor editor = sharedPref.edit();

        Button webViewButton = viewGroup.findViewById(R.id.webView_button);
        webViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingWebViewActivity.class);
                startActivity(intent);
            }
        });

        Button saveButton = viewGroup.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText commentEdit = view.findViewById(R.id.comment);

                String attendUrl = sharedPref.getString("attend_url","");
                String commentStr = commentEdit.getText().toString();

                if (isEmptyStrings(attendUrl, commentStr)){
                    Toast.makeText(getActivity(), "저장실패", LENGTH_SHORT).show();
                }else{
                    editor.putString("comment",commentStr);
                    editor.apply();
                    Toast.makeText(getActivity(), "저장성공", LENGTH_SHORT).show();
                    Objects.requireNonNull(getActivity()).finish();
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