package it.ezzie.smartalarm;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDatabase();
        initUI();
        initDialog();
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
    private void initDialog(){
        //Floating Btn
        var dialogBinding = ActivityEditAlarmBinding.inflate(getLayoutInflater());
        binding.floatingBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            alertDialog = builder.setView(dialogBinding.getRoot())
                    .setCancelable(false)
                    .create();
            alertDialog.setOnShowListener(dialog -> {
                alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog);
            });
            alertDialog.show();
        });
        dialogBinding.btnCancel.setOnClickListener(v -> {
            alertDialog.cancel();
        });

        dialogBinding.timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(Calendar.MINUTE,minute);
            var formattedHour = new SimpleDateFormat("HH").format(calendar.getTime());
            var formattedMinute = new SimpleDateFormat("mm").format(calendar.getTime());
            dialogBinding.hour.setText(formattedHour.toString());
            dialogBinding.minute.setText(formattedMinute.toString());
        });

    }


}