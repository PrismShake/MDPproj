package com.example.mdpproj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RegisterPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_page);
    }
}