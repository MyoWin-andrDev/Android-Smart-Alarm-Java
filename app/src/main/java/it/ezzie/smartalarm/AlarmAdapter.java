package it.ezzie.smartalarm;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import it.ezzie.smartalarm.Entity.AlarmEntity;
import it.ezzie.smartalarm.databinding.AdapterAlarmBinding;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {
    private Context context;
    private List<AlarmEntity> alarmList;
    private Calendar calendar = Calendar.getInstance();

    public AlarmAdapter(Context context , List<AlarmEntity> alarmList){
        this.context = context;
        this.alarmList = alarmList;
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder{
        private AdapterAlarmBinding binding;
        public AlarmViewHolder(AdapterAlarmBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var binding = AdapterAlarmBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new AlarmViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        var alarm = alarmList.get(position);
        //InitTimePick
        holder.binding.alarmTime.setOnClickListener(v -> {
                TimePickerDialog.OnTimeSetListener timepick = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        var formattedTime = new SimpleDateFormat("HH:mm").format(calendar.getTime());
                        var formattedUnit = new SimpleDateFormat("a").format(calendar.getTime());
                        holder.binding.alarmTime.setText(formattedTime);
                        holder.binding.alarmUnit.setText(formattedUnit.toUpperCase());
                    }
                };
                new TimePickerDialog(context,timepick, calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();

        });
        holder.binding.alarmLabel.setText(alarm.getAlarmLabel());
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

}
