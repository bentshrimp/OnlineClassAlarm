package com.example.alarm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Objects;

// TODO : 여기에 메인화면 구현
public class MainFragment extends Fragment {

    TextView alarmTimeTextView;

    Context context;

    AdView mAdView;

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

        SharedPreferences sharedPref = context.getSharedPreferences("school", Context.MODE_PRIVATE);

        String alarmTime = sharedPref.getString("alarm_time","");
        assert alarmTime != null;
        if (alarmTime.getBytes().length<=0){
            alarmTimeTextView.setText("알람시각 : 아직 정하지 않음");
        }else{
            alarmTimeTextView.setText("알람시각 : "+alarmTime);
        }

        addAd(viewGroup);

        return viewGroup;
    }

    public void addAd(ViewGroup viewGroup){
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        mAdView = viewGroup.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}