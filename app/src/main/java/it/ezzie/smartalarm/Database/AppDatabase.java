package it.ezzie.smartalarm.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import it.ezzie.smartalarm.Data_Access_Object.AlarmDAO;
import it.ezzie.smartalarm.Entity.AlarmEntity;

@Database(entities = {AlarmEntity.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AlarmDAO alarmDAO();
    public static AppDatabase appDatabase;
    public synchronized static AppDatabase getInstance(Context context){
        if(appDatabase == null){
            appDatabase = Room.databaseBuilder(context, AppDatabase.class,"AlarmDB").allowMainThreadQueries().build();
        }
        return appDatabase;
    }
}
