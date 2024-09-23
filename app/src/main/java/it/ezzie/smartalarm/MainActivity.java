package it.ezzie.smartalarm;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import it.ezzie.smartalarm.Data_Access_Object.AlarmDAO;
import it.ezzie.smartalarm.Database.AppDatabase;
import it.ezzie.smartalarm.Entity.AlarmEntity;
import it.ezzie.smartalarm.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private List<AlarmEntity> alarmList;
    private AlarmDAO alarmDAO;
    private AlarmAdapter alarmAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDatabase();
        initUI();
    }

    private void initDatabase() {
        var db = AppDatabase.getInstance(this);
        alarmDAO = db.alarmDAO();
        alarmDAO.createAlarm(new AlarmEntity("4:00","Wake UP"));
        alarmList = alarmDAO.getAllAlarms();
    }
    private void initUI(){
        alarmAdapter = new AlarmAdapter(this,alarmList);
        binding.recyclerView.setAdapter(alarmAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void initListener(){
        binding.floatingBtn.setOnClickListener(v -> {
          //  Intent intent = new Intent(this,)
        });
    }


}