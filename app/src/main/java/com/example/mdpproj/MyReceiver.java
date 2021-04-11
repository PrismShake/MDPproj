package com.example.mdpproj;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context , Intent intent) {
        String task = intent.getStringExtra ( "task" );
        Toast.makeText ( context, task, Toast.LENGTH_LONG).show();
    }
}
