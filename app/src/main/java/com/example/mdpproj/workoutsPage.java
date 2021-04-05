package com.example.mdpproj;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class workoutsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        getSupportActionBar().hide();
        setContentView ( R.layout.activity_workouts_page );
    }
}