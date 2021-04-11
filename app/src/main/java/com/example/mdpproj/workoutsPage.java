package com.example.mdpproj;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

public class workoutsPage extends AppCompatActivity {
    AlarmManager alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        getSupportActionBar().hide();
        setContentView ( R.layout.activity_workouts_page );
        alarmManager = (AlarmManager) getSystemService ( Context.ALARM_SERVICE );
    }
    public void doTask(View view) {
        SeekBar taskView = findViewById ( R.id.multseekBar );
        String task = taskView.getText().toString ();

        Log.i("appRem", "task"+task);

        EditText timerView = findViewById ( R.id.editTextNumber );
        String timerString = timerView.getText().toString ();
        long timerInSec = Long.parseLong(timerString);

        Intent intent = new Intent(this, MyReceiver.class);
        intent.putExtra ( "task", task );

        //sentBroadcast(intent);
        PendingIntent alarmIntent = PendingIntent.getBroadcast (this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT  );


        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            alarmManager.cancel ( alarmIntent );
            alarmManager.setExactAndAllowWhileIdle ( AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime ( )+ timerInSec * 1000, alarmIntent  );
        }else{
            alarmManager.cancel ( alarmIntent );
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime ( )+ timerInSec * 1000, alarmIntent);
        }
    }
}