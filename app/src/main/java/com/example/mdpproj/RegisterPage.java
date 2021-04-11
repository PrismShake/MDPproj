package com.example.mdpproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegisterPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_page);
    }
    public void goToSignIn(View view) {
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        startActivity(intent);

    }
}