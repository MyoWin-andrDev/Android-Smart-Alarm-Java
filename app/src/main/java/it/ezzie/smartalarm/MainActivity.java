package it.ezzie.smartalarm;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import it.ezzie.smartalarm.Data_Access_Object.AlarmDAO;
import it.ezzie.smartalarm.Database.AppDatabase;
import it.ezzie.smartalarm.Entity.AlarmEntity;
import it.ezzie.smartalarm.databinding.ActivityEditAlarmBinding;
import it.ezzie.smartalarm.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private List<AlarmEntity> alarmList;
    private AlarmDAO alarmDAO;
    private AlarmAdapter alarmAdapter;
    private AlertDialog alertDialog;
    private Calendar calendar = Calendar.getInstance();
    private int resultHour;
    private int resultMinute;
    private static final int CREATE_REQUEST_CODE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDatabase();
        initUI();
//        initDialog();
        initListener();
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
//    private void initDialog(){
//        //Floating Btn
//        var dialogBinding = ActivityEditAlarmBinding.inflate(getLayoutInflater());
//        binding.floatingBtn.setOnClickListener(v -> {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            alertDialog = builder.setView(dialogBinding.getRoot())
//                    .setCancelable(false)
//                    .create();
//            alertDialog.setOnShowListener(dialog -> {
//                alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog);
//            });
//            alertDialog.show();
//        });
//
//        dialogBinding.timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
//          calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//          calendar.set(Calendar.MINUTE,minute);
//
//          int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//          int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
//          int totalCurrentMinute = currentHour * 60 + currentMinute;
//          int totalSelectedMinute = hourOfDay * 60 + minute;
//          int totalResultMinute = totalSelectedMinute - totalCurrentMinute;
//          int resultHour = totalResultMinute / 60;
//          int resultMinute = totalResultMinute % 60;
//          if(totalResultMinute < 0){
//              resultHour = -resultHour;
//              resultMinute = -resultMinute;
//          }
//          dialogBinding.hour.setText(String.valueOf(resultHour));
//          dialogBinding.minute.setText(String.format("%02d",resultMinute));
//        });
//
//    }
//    private void initListener(){
//        dialogBinding.btnCancel.setOnClickListener(v -> {
//            alertDialog.cancel();
//        });
//    }
    private void initListener(){
        binding.floatingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditAlarm.class);
            startActivityForResult(intent,CREATE_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CREATE_REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                AlarmEntity alarm = (AlarmEntity) data.getSerializableExtra("alarm");
                alarmDAO.createAlarm(alarm);
                refreshView();
            }
        }
    }

    private void refreshView() {
        alarmList = alarmDAO.getAllAlarms();
        alarmAdapter.setAlarm();
    }
}