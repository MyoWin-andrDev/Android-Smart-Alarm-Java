package it.ezzie.smartalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import it.ezzie.smartalarm.Alarm_Receiver.AlarmReceiver;
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
          binding.timeCount.setText(new StringBuilder().append(" Your alarm will ring in ").append(String.valueOf(resultHour)).append(" hr ").append(String.format("%02d", resultMinute)).append(" min.").toString());
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
                  //Update
                  String finalHour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
                  String finalMinute = String.valueOf(calendar.get(Calendar.MINUTE));
                  AlarmEntity alarm;
                  String label = binding.alarmEditTxt.getText().toString().trim();
              if(getIntent() != null) {
                  if (alarmList != null) {
                      if (!finalHour.equals(alarmList.getAlarmHour()) || !finalMinute.equals(alarmList.getAlarmMinute()) || !label.equals(alarmList.getAlarmLabel())) {
                          alarmList.setAlarmHour(finalHour);
                          alarmList.setAlarmMinute(finalMinute);
                          alarmList.setAlarmUnit(formattedUnit);
                          alarmList.setAlarmLabel(label);
                          alarmList.setAlarmOn(true);
                          alarmDAO.updateAlarm(alarmList);
                          finish();
                      } else {
                          finish();
                      }
                  }
              }
              //Create
              //Getting Alarm Label
              if(label.isEmpty()){
                  alarm = new AlarmEntity(String.valueOf(hourOfDay),String.format("%02d",minute),formattedUnit,true);
              }
              else {
                   alarm = new AlarmEntity(String.valueOf(hourOfDay), String.format("%02d", minute),formattedUnit,true, label);
              }
              scheduleAlarm(this,alarm);
              Intent intent = new Intent(this, MainActivity.class);
              intent.putExtra("alarm",alarm);
              setResult(RESULT_OK,intent);
              finish();
          });
        });
    }
    public void scheduleAlarm(Context context, AlarmEntity alarm){
        AlarmManager alarmManager = context.getSystemService(AlarmManager.class);
        Intent intent = new Intent(context ,AlarmReceiver.class);
        intent.putExtra("alarmLabel", alarm.getAlarmLabel());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,Integer.parseInt(alarm.getAlarmHour()) * 100 + Integer.parseInt(alarm.getAlarmMinute()), intent , PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        //Init Calendar
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(alarm.getAlarmHour()));
        calendar1.set(Calendar.MINUTE, Integer.parseInt(alarm.getAlarmMinute()));
        calendar1.set(Calendar.SECOND , 0);
        //Set the alarm to ring
        if(alarm.isAlarmOn()){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(),pendingIntent);
        }
        else {
            alarmManager.cancel(pendingIntent);
        }
    }
    //Update UI
    private void initUI() {
        if (getIntent() != null) {
            alarmList = (AlarmEntity) getIntent().getSerializableExtra("alarm");
            if (alarmList != null) {
                binding.timePicker.setHour(Integer.parseInt(alarmList.getAlarmHour()));
                binding.timePicker.setMinute(Integer.parseInt(alarmList.getAlarmMinute()));
                binding.alarmEditTxt.setText(alarmList.getAlarmLabel());
                binding.btnCancel.setText("Delete");
                binding.btnOK.setText("Update");
            }
        }
    }

}
