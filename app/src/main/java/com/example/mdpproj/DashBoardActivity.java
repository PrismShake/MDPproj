package com.example.mdpproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class DashBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate ( savedInstanceState );
        getSupportActionBar().hide();
        setContentView ( R.layout.activity_tab );

    }
    //these are all click events where intents are are started, used for navigation
    public void openProfile(View view) {
        Intent intent = new Intent(view.getContext(), UserProfilePageActivity.class);
        startActivity(intent);
    }

    public void openWorkouts(View view) {
        Intent intent = new Intent(view.getContext(), workoutsPage.class);
        startActivity(intent);
    }
    public void openBuddies(View view) {
        Intent intent = new Intent(view.getContext(), BuddiesMainPageActivity.class);
        startActivity(intent);
    }
    public void openMessagesOne(View view) {
        Intent intent = new Intent(view.getContext(), ChatDisplayPageActivity.class);
        startActivity(intent);
    }
    public void openMessagesTwo(View view) {
        Intent intent = new Intent(view.getContext(), MessagingActivity.class);
        startActivity(intent);
    }


    public void Logout(View view) {
        FirebaseAuth.getInstance ( ).signOut ();
        Intent intent = new Intent(this,MainActivity.class );
        intent.addFlags ( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity ( intent );
        return;
    }
}