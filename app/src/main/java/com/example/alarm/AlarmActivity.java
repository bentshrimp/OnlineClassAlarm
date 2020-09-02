package com.example.alarm;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.View;

public class AlarmActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        // 알람음 재생
        this.mediaPlayer = MediaPlayer.create(this, R.raw.ring_tone);
        this.mediaPlayer.setLooping(true);
        this.mediaPlayer.start();

        findViewById(R.id.btnClose).setOnClickListener(mClickListener);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        AudioManager mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);//선언 후
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP :


                mAudioManager.setStreamVolume(AudioManager.STREAM_RING,

                        (int)(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING) * 0.90),

                        AudioManager.FLAG_PLAY_SOUND);
// * 0.90 이 부분의 숫자에 맞춰 볼륨이 바뀝니다. (*0.25 이면 25%의 볼륨)


                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
// ring volume down
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
    private void close() {
        if (this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.stop();
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }

        finish();
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnClose:
                    // 알람 종료
                    close();

                    break;
            }
        }
    };
}