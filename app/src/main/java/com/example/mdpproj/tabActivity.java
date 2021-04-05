package com.example.mdpproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class tabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        getSupportActionBar().hide();
        setContentView ( R.layout.activity_tab );
    }
    public void openProfile(View view) {
        Intent intent = new Intent(view.getContext(), profilePage.class);
        startActivity(intent);
    }

    public void openWorkouts(View view) {
        Intent intent = new Intent(view.getContext(), workoutsPage.class);
        startActivity(intent);
    }
    public void openBuddies(View view) {
        Intent intent = new Intent(view.getContext(), buddiesPage.class);
        startActivity(intent);
    }
    public void openMessagesOne(View view) {
        Intent intent = new Intent(view.getContext(), messagePageOne.class);
        startActivity(intent);
    }
    public void openMessagesTwo(View view) {
        Intent intent = new Intent(view.getContext(), messagePageTwo.class);
        startActivity(intent);
    }
}