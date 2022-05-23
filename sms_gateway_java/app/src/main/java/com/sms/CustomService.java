package com.sms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Telephony;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class CustomService extends Service  {

    private BroadcastReceiver br ;
    private LooperThread looperThread;
    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }
    // TODO : fix background running
    @Override
    public void onDestroy() {
        // If we get killed, after returning from here, restart
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), CustomService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 100, pendingIntent);
        Log.wtf("onDestroy from Service", "service done");
    }

    @Override
    public void onCreate() {
        this.looperThread = new LooperThread();
        this.looperThread.start();
        this.br = new CustomReceiver();
//        this.br = new CustomReceiver(looperThread);
        Log.wtf("onCreate from Service", "CALLED");
        registerReceiver(br, new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        Log.wtf("onStartCommand from Service", "CALLED");
        return START_STICKY;
    }

    // TODO : fix background running
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // If we get killed, after returning from here, restart
        Log.wtf("onTaskRemoved", "onTaskRemoved");
        //create a intent that you want to start again..
        Intent intent = new Intent(getApplicationContext(), CustomService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 100, pendingIntent);
        super.onTaskRemoved(rootIntent);
    }


}
