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
    void createAlarm();
    @Update
    void updateAlarm();
    @Delete
    void deleteAlarm();
    @Query("SELECT * FROM Alarms")
    List<AlarmEntity> getAllAlarms();
}
