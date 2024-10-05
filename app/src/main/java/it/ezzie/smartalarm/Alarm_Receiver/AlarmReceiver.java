package it.ezzie.smartalarm.Alarm_Receiver;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import it.ezzie.smartalarm.R;
import it.ezzie.smartalarm.databinding.ActivityAlarmReceiverBinding;

public class AlarmReceiver extends AppCompatActivity {
    private ActivityAlarmReceiverBinding binding;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAlarmReceiverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initMedia();
    }

    private void initMedia() {
        mediaPlayer = MediaPlayer.create(this, R.raw.playing_god);
        mediaPlayer.start();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                finally {
                    mediaPlayer.stop();
                }
            }
        });
    }
}