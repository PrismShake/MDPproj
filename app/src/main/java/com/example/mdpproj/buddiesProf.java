package com.example.mdpproj;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class buddiesProf extends AppCompatActivity {
    DatabaseReference mRoot;
    FirebaseAuth auth;
    String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    TextView fullName, age, pronouns, state, city, workout, motivation, gym, uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_buddies_prof );

        uname = findViewById(R.id.uName);
        fullName = findViewById(R.id.FullName);
        age = findViewById(R.id.Age);
        pronouns = findViewById(R.id.Pronouns);
        state = findViewById(R.id.State);
        city = findViewById(R.id.City);
        workout = findViewById(R.id.Workout);
        motivation = findViewById(R.id.Motivation);
        gym = findViewById(R.id.Gym);
        mRoot = FirebaseDatabase.getInstance().getReference("Users");
    }
}