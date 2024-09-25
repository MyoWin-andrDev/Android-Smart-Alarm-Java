package it.ezzie.smartalarm;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


import it.ezzie.smartalarm.Data_Access_Object.AlarmDAO;
import it.ezzie.smartalarm.Database.AppDatabase;
import it.ezzie.smartalarm.Entity.AlarmEntity;
import it.ezzie.smartalarm.databinding.AdapterAlarmBinding;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {
    private Context context;
    private List<AlarmEntity> alarmList;
    private Calendar calendar = Calendar.getInstance();
    private AlarmClickListener listener;
    private AlarmDAO alarmDAO = AppDatabase.appDatabase.alarmDAO();
    public AlarmAdapter(Context context , List<AlarmEntity> alarmList, AlarmClickListener listener){
        this.context = context;
        this.alarmList = alarmList;
        this.listener = listener;
    }

    public void setAlarm(List<AlarmEntity> alarmList) {
        this.alarmList = alarmList;
        this.notifyDataSetChanged();
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder{
        private AdapterAlarmBinding binding;
        public AlarmViewHolder(AdapterAlarmBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface AlarmClickListener{
        void onAlarmClicked(AlarmEntity alarm);
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
                TimePickerDialog.OnTimeSetListener timePick = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        var formattedTime = new SimpleDateFormat("HH:mm").format(calendar.getTime());
                        var formattedHour = new SimpleDateFormat("HH").format(calendar.getTime());
                        var formattedMinute = new SimpleDateFormat("mm").format(calendar.getTime());
                        var formattedUnit = new SimpleDateFormat("a").format(calendar.getTime());
                        holder.binding.alarmUnit.setText(formattedUnit.toUpperCase());
                        holder.binding.alarmHour.setText(formattedHour);
                        holder.binding.alarmMinute.setText(formattedMinute);
                        holder.binding.alarmSwitch.setChecked(true);
                        if(formattedUnit.equals("PM")){
                            holder.binding.imageView.setImageResource(R.drawable.ic_moon);
                        }else if(formattedUnit.equals("AM")){
                            holder.binding.imageView.setImageResource(R.drawable.ic_sun);
                        }
                    }
                };
                new TimePickerDialog(context,timePick, calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();

        });
        holder.binding.alarmLabel.setText(alarm.getAlarmLabel());
        holder.binding.alarmHour.setText(alarm.getAlarmHour());
        holder.binding.alarmMinute.setText(alarm.getAlarmMinute());
        holder.binding.alarmSwitch.setChecked(alarm.isAlarmOn());
        holder.binding.alarmUnit.setText(alarm.getAlarmUnit().toUpperCase());
        if(alarm.getAlarmUnit().equals("PM")){
            holder.binding.imageView.setImageResource(R.drawable.ic_moon);
        }
        else if(alarm.getAlarmUnit().equals("AM")){
            holder.binding.imageView.setImageResource(R.drawable.ic_sun);
        }
        var alarms = alarmList.get(position);
        holder.binding.alarmSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                holder.binding.alarmSwitch.setChecked(true);
                alarms.setAlarmOn(true);

            }
            else{
                holder.binding.alarmSwitch.setChecked(false);
                alarms.setAlarmOn(false);
            }
            alarmDAO.updateAlarm(alarms);
        });
        holder.binding.listLinear.setOnClickListener(v -> {
            listener.onAlarmClicked(alarms);
        });
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

}
