package it.ezzie.smartalarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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
    private final EditAlarm editAlarm = new EditAlarm();
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
        int resultMinute = Integer.parseInt(alarm.getAlarmMinute());
        holder.binding.alarmLabel.setText(alarm.getAlarmLabel());
        holder.binding.alarmHour.setText(alarm.getAlarmHour());
        holder.binding.alarmMinute.setText(String.format("%02d",resultMinute));
        holder.binding.alarmSwitch.setChecked(alarm.isAlarmOn());
        holder.binding.alarmUnit.setText(alarm.getAlarmUnit().toUpperCase());
        if(alarm.getAlarmUnit().equalsIgnoreCase("PM")){
            holder.binding.imageView.setImageResource(R.drawable.ic_moon);
        }
        else if(alarm.getAlarmUnit().equalsIgnoreCase("AM")){
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
            editAlarm.scheduleAlarm(alarms);
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
