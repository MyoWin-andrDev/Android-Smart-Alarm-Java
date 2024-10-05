package it.ezzie.smartalarm.Alarm_Receiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import it.ezzie.smartalarm.R;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String alarmLabel;
        if(intent.getStringExtra("alarmLabel") == null){
            alarmLabel = "Alarm is ringing";
        }else{
            alarmLabel = intent.getStringExtra("alarmLabel");
        }
        createChannel(context, alarmLabel);
    }
    private void createChannel(Context context , String alarmLabel){
        String channelId = "alarm_channel";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //Create Ringtone
            Uri ringtoneUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.playing_god);
            //Creating Notification Channel
            NotificationChannel notificationChannel = new NotificationChannel(channelId, "Alarm Notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setSound(ringtoneUri, null);
            //Creating Notification Manager
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
            //Creating Notification Builder
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                    .setContentTitle("Alarm")
                    .setContentText(alarmLabel)
                    .setSmallIcon(R.drawable.alarm_icon)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            notificationManager.notify(1, builder.build());
        }
    }
}
