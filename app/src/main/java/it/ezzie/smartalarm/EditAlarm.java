package it.ezzie.smartalarm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import it.ezzie.smartalarm.Data_Access_Object.AlarmDAO;
import it.ezzie.smartalarm.Database.AppDatabase;
import it.ezzie.smartalarm.Entity.AlarmEntity;
import it.ezzie.smartalarm.databinding.ActivityEditAlarmBinding;

public class EditAlarm extends AppCompatActivity {
    private ActivityEditAlarmBinding binding;
    private AlarmDAO alarmDAO;
    private Calendar calendar = Calendar.getInstance();
    private AlarmEntity alarmList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityEditAlarmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDatabase();
        initData();
        initUI();
    }

    private void initDatabase() {
        var databaseBuilder = AppDatabase.getInstance(this);
        alarmDAO = databaseBuilder.alarmDAO();
        alarmDAO.getAllAlarms();
    }
    private void initData(){
          binding.timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
          calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
          calendar.set(Calendar.MINUTE,minute);

          int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
          int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
          int totalCurrentMinute = currentHour * 60 + currentMinute;
          int totalSelectedMinute = hourOfDay * 60 + minute;
          int totalResultMinute = totalSelectedMinute - totalCurrentMinute;
          int resultHour = totalResultMinute / 60;
          int resultMinute = totalResultMinute % 60;
          if(totalResultMinute < 0){
              resultHour = -resultHour;
              resultMinute = -resultMinute;
          }
          binding.hour.setText(String.valueOf(resultHour));
          binding.minute.setText(String.format("%02d",resultMinute));

          //Init AlarmUnit
            var formattedUnit = new SimpleDateFormat("a").format(calendar.getTime());

          //Cancel Button
          binding.btnCancel.setOnClickListener(v -> {
              if(getIntent() != null){
                  alarmDAO.deleteAlarm(alarmList);
                  finish();
              }
              finish();
          });

          //OK Button
              binding.btnOK.setOnClickListener(v -> {
              String finalHour = String.valueOf(finalResultHour);
              if(getIntent() != null){

              }
              //Create
              AlarmEntity alarm;
              //Getting Alarm Label
                  String label = binding.alarmEditTxt.getText().toString().trim();
              if(label.isEmpty()){
                  alarm = new AlarmEntity(String.valueOf(hourOfDay),String.format("%02d",minute),formattedUnit);
              }
              else {
                   alarm = new AlarmEntity(String.valueOf(hourOfDay), String.format("%02d", minute),formattedUnit, label);
              }
              Intent intent = new Intent(this, MainActivity.class);
              intent.putExtra("alarm",alarm);
              setResult(RESULT_OK,intent);
              finish();
          });
        });
    }
    private void initUI() {
        if (getIntent() != null) {
            alarmList = (AlarmEntity) getIntent().getSerializableExtra("alarm");
            if (alarmList != null) {
                binding.timePicker.setHour(Integer.parseInt(alarmList.getAlarmHour()));
                binding.timePicker.setMinute(Integer.parseInt(alarmList.getAlarmMinute()));
                binding.alarmEditTxt.setText(alarmList.getAlarmLabel());
                binding.btnCancel.setText("Delete");
                binding.btnOK.setText("Update");
                Log.d("EditAlarm", "Alarm loaded: " + alarmList.getAlarmHour() + ":" + alarmList.getAlarmMinute());
            } else {
                Log.d("EditAlarm", "No alarm data found in Intent.");
            }
        } else {
            Log.d("EditAlarm", "Intent is null.");
        }
    }

}
