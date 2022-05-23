package com.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;

import java.util.concurrent.TimeUnit;

public class CustomReceiver extends BroadcastReceiver {

    private static final String TAG = "BroadcastReceiver";
    private static final String pdu_type = "pdus";
    private  String WORKER_TAG ="SUS";
    private LooperThread looperThread;

    CustomReceiver(LooperThread _looperThread) {
        super();
        this.looperThread = _looperThread;
    }

    CustomReceiver() {
        super();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Intent recieved: " + intent.getAction());
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            // Get the SMS message.
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs;
            String strMessage = "";
            String from = "";
            String format = bundle.getString("format");
            // Retrieve the SMS message received.
            Object[] pdus = (Object[]) bundle.get(pdu_type);

            // Fill the msgs array.
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                // Build the message to show.
                from = "SMS from " + msgs[i].getOriginatingAddress();
                strMessage += " :" + msgs[i].getMessageBody() + "\n";
            }

            // Log and display the SMS message.
            Log.d(TAG, "onReceive: " + from + strMessage);
            Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show();

            Constraints constraints = new Constraints
                    .Builder()
                    .setRequiredNetworkType(NetworkType.NOT_ROAMING)
                    .build();

            Data msg = new Data.Builder()
                    .putString("from", from)
                    .putString("msg", strMessage)
                    .build();

            OneTimeWorkRequest uploadWorkRequest =
                    new OneTimeWorkRequest.Builder(UpdateWorker.class)
                            .setConstraints(constraints)
                            .setInputData(msg)
                            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 1, TimeUnit.MINUTES)
                            .build();

            WorkManager.getInstance(context).enqueue(uploadWorkRequest);



        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void parseMessage(String from, String body) {
        Message msg = new Message();
        msg.obj = body;
        if (looperThread != null) {
            looperThread.mHandler.sendMessage(msg);
        }
    }
}
