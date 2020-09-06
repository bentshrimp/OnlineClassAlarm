package com.example.alarm;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class AlarmActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

    AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
    audioManager.setStreamVolume(AudioManager.STREAM_RING,(audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)), AudioManager.FLAG_PLAY_SOUND);

        // 알람음 재생
        this.mediaPlayer = MediaPlayer.create(this, R.raw.ring_tone);
        this.mediaPlayer.setLooping(true);
        this.mediaPlayer.start();

        Button closeButton = findViewById(R.id.btnClose);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlarmActivity.this, WebViewActivity.class);
                startActivity(intent);
                mediaPlayerclose();
            }
        });

        addAd();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        AudioManager mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);//선언 후
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP :


                mAudioManager.setStreamVolume(AudioManager.STREAM_RING,

                        (int)(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING) * 1),

                        AudioManager.FLAG_PLAY_SOUND); // * 0.90 이 부분의 숫자에 맞춰 볼륨이 바뀝니다. (*0.25 이면 25%의 볼륨)


                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN: // ring volume down
                mAudioManager.setStreamVolume(AudioManager.STREAM_RING,

                        (int)(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING) * 0.25),

                        AudioManager.FLAG_PLAY_SOUND); return true;
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // MediaPlayer release
        if (this.mediaPlayer != null) {
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }
    }

    /* 알람 종료 */
    private void mediaPlayerclose() {
        if (this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.stop();
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }

        finish();
    }

    public void addAd(){
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}