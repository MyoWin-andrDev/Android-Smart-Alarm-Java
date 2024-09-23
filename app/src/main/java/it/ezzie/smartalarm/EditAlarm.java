package it.ezzie.smartalarm;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import it.ezzie.smartalarm.databinding.ActivityEditAlarmBinding;

public class EditAlarm extends AppCompatActivity {
    private ActivityEditAlarmBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityEditAlarmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}