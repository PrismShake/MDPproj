package com.example.mdpproj;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
//for reminder function in workouts page
public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context , Intent intent) {
        String task = intent.getStringExtra ( "task" );
        Toast.makeText ( context, task, Toast.LENGTH_LONG).show();
    }
}
