package it.ezzie.smartalarm.Data_Access_Object;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.ezzie.smartalarm.Entity.AlarmEntity;

@Dao
public interface AlarmDAO {
    @Insert
    void createAlarm(AlarmEntity alarm);
    @Update
    void updateAlarm(AlarmEntity alarm);
    @Delete
    void deleteAlarm(AlarmEntity alarm);
    @Query("SELECT * FROM Alarms")
    List<AlarmEntity> getAllAlarms();
}
