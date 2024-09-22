package it.ezzie.smartalarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import it.ezzie.smartalarm.Entity.AlarmEntity;
import it.ezzie.smartalarm.databinding.AdapterAlarmBinding;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {
    private Context context;
    private List<AlarmEntity> alarmList;
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
        holder.binding.alarmTime.setText(alarm.getAlarmTime());
        holder.binding.alarmLabel.setText(alarm.getAlarmLabel());
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }


}
