package it.ezzie.smartalarm.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Alarms")
public class AlarmEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "Time")
    private String alarmTime;
    @ColumnInfo(name = "Label")
    private String alarmLabel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmLabel() {
        return alarmLabel;
    }

    public void setAlarmLabel(String alarmLabel) {
        this.alarmLabel = alarmLabel;
    }
}
