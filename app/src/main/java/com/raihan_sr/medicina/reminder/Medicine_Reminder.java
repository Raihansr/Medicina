package com.raihan_sr.medicina.reminder;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import com.raihan_sr.medicina.util.Utils;
import com.raihan_sr.medicina.content.MainActivity;
import com.raihan_sr.medicina.database.MySQLiteDB;
import com.raihan_sr.medicina.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Medicine_Reminder extends BroadcastReceiver {

    public static int NOTIFICATION_ID = 1;

    @SuppressLint("WakelockTimeout")
    @Override public void onReceive(Context context, Intent intent) {

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        try {
            if(pm != null){
                PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, null);

                wl.acquire();

                final Cursor cursor = new MySQLiteDB(context).displayMedicineData();

                cursor.moveToFirst();

                while(!cursor.isAfterLast()) {

                    String Alarm_Times = cursor.getString(5);
                    String Alarm_Choose = cursor.getString(6);
                    String Current_Time = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());

                    if(Alarm_Times.contains(Current_Time) && (Alarm_Times+Alarm_Choose).contains("Alarm_On") && !Utils.notification_Sent){

                        String getTime =  new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
                        String title = "Take Medicine";
                        String message = "It's " + getTime + ", Please take your medicine";

                        showNotification(context, title, message);

                        Utils.notification_Sent = true;
                    }

                    if(Utils.notification_Sent){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Utils.notification_Sent = false;
                            }
                        }, 60*1000);
                    }

                    cursor.moveToNext();
                }

                wl.release();
            }
        }
        catch (Exception ex){
            Log.i(Utils.TAG, "Medicine Reminder Wake Lock Exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @SuppressLint("ShortAlarm")
    public void setAlarm(Context context){

        final int _id = (int) System.currentTimeMillis();

        Intent myIntent = new Intent(context, Medicine_Reminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, _id, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if(alarmManager != null){
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(),30*1000, pendingIntent);
        }
    }

    public void cancelAlarm(Context context){

        Intent myIntent = new Intent(context, Medicine_Reminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(alarmManager != null){
            alarmManager.cancel(pendingIntent);
        }
    }

    private void showNotification(Context context, String title, String message) {
        Intent intent= new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("notificationKey", "notification");
        PendingIntent contentIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_ONE_SHOT);

        String Channel_ID = "com.raihan_sr.medicina";
        String Channel_Name = "Medicina";

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Channel_ID);
        builder.setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(contentIntent)
                .setColor(context.getResources().getColor(R.color.colorAccent))
                .setAutoCancel(true)
                .setVibrate(new long[] { 100, 100, 100, 100, 100 })
                .setContentTitle(title)
                .setContentText(message)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setDefaults(Notification.DEFAULT_LIGHTS);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(Channel_ID, Channel_Name, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(message);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(Color.CYAN);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            assert notificationManager != null;
            builder.setChannelId(Channel_ID);
            notificationManager.createNotificationChannel(channel);
        }

        if (NOTIFICATION_ID > 1073741824) {
            NOTIFICATION_ID = 0;
        }

        assert notificationManager != null;
        notificationManager.notify(NOTIFICATION_ID++,builder.build());
    }
}
