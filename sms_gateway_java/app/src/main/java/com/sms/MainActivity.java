package com.sms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * This app provides SMS features that enable the user to:
 * - Enter a phone number.
 * - Enter a message and send the message to the phone number.
 * - Receive SMS messages and display them in a toast.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, CustomService.class);
        this.startService(intent);
//test
    }

}