package it.ezzie.smartalarm.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Alarms")
public class AlarmEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "Hour")
    private String alarmHour;
    @ColumnInfo(name = "Minute")
    private String alarmMinute;
    @ColumnInfo(name = "Label")
    private String alarmLabel;

    public AlarmEntity(){

    }
    @Ignore
    public AlarmEntity( String alarmHour, String alarmMinute){
        this.alarmHour = alarmHour;
        this.alarmMinute = alarmMinute;
    }
    @Ignore
    public AlarmEntity( String alarmHour, String alarmMinute, String alarmLabel){
        this.alarmHour = alarmHour;
        this.alarmMinute = alarmMinute;
        this.alarmLabel = alarmLabel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlarmHour() {
        return alarmHour;
    }

    public void setAlarmHour(String alarmHour) {
        this.alarmHour = alarmHour;
    }

    public String getAlarmMinute() {
        return alarmMinute;
    }

    public void setAlarmMinute(String alarmMinute) {
        this.alarmMinute = alarmMinute;
    }

    public String getAlarmLabel() {
        return alarmLabel;
    }

    public void setAlarmLabel(String alarmLabel) {
        this.alarmLabel = alarmLabel;
    }
}
