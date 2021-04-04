package com.example.mdpproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
    }

    public void registerUser(View view) {
        Intent intent = new Intent(this,RegisterPage.class);
        startActivity(intent);
        Log.i("mobdev","Hello");
    }
}