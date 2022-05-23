package com.sms;

import android.os.Looper;


class LooperThread extends Thread {
    public CustomHandler mHandler;

    public void run() {
        Looper.prepare();

        mHandler = new CustomHandler(Looper.myLooper());

        Looper.loop();
    }
}