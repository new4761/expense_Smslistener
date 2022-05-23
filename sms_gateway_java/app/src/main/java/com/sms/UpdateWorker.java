package com.sms;


import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class UpdateWorker extends Worker {

    private static final String TAG = "UpdateWorker";

    public UpdateWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {

        // Do the work here--in this case, upload the images.

        if (chkIsWifi()) {
            Log.d(TAG, this.getInputData().getString("from")  + this.getInputData().getString("msg"));
            // Indicate whether the work finished successfully with the Result
            return Result.success();
        }
        // get back to queue for make sure is saved on database
        return Result.retry();
    }


    boolean chkIsWifi() {
        final ConnectivityManager connMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifi.isConnectedOrConnecting()) {
            Log.d(TAG, "doWork: " + this.getInputData().getString("test"));
            return true;
        }
        return false;
    }

}
