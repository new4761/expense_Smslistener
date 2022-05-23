package com.sms;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.LinkedList;
import java.util.Queue;


public class CustomHandler extends Handler {
    private String TAG = "RunLoopApp";
    private Queue<Message> msgQ =new LinkedList<Message>();
    CustomHandler(Looper myLooper) {
        super(myLooper);
    }

    @Override
    public void handleMessage(Message msg) {
        Log.d(TAG, ">>>>>>handle message: " + msg);
        //TODO : add logic to send to check wifi and push to rabbitmq or other broker
        msgQ.add(msg);
        try {
            Log.d(TAG, String.valueOf(msgQ.size()));
        } catch (Exception err) {
            Log.e(TAG, err.toString());
        }
    }
}
