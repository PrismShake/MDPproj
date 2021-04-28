package com.example.mdpproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class buddiesProf extends AppCompatActivity {
    DatabaseReference mRoot;
    FirebaseAuth auth;
    String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    TextView fullName, age, pronouns, state, city, workout, motivation, gym, uname;
    private ImageView profileImageView;
    Button match;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_buddies_prof );

        match = findViewById(R.id.match);
        match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                match.setText("matched");
                Users u = new Users();
            }
        });
        uname = findViewById(R.id.uName);
        fullName = findViewById(R.id.FullName);
        age = findViewById(R.id.Age);
        pronouns = findViewById(R.id.Pronouns);
        state = findViewById(R.id.State);
        city = findViewById(R.id.City);
        workout = findViewById(R.id.Workout);
        motivation = findViewById(R.id.Motivation);
        gym = findViewById(R.id.Gym);
        profileImageView = findViewById(R.id.prof);
        mRoot = FirebaseDatabase.getInstance().getReference("Users");
        Intent intent = getIntent();
        String currentDude = intent.getStringExtra("name");


        //where data is read from database and used to update the profile accordingly
        //based off the users response to the questionaire

        //listens to whether get is successful or not
        mRoot.child(currentDude).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot> () {
            @Override

            public void onSuccess(DataSnapshot dataSnapshot) {
                Users u = dataSnapshot.getValue(Users.class);
                String user_name = u.getUserName();
                uname.setText(user_name);
                fullName.setText(u.getFull_name());
                age.setText(u.getAge());
                pronouns.setText(u.getPronouns());
                city.setText(u.getCity());
                state.setText(u.getState());
                workout.setText(u.getWorkout());
                motivation.setText(u.getMotivation());
                gym.setText(u.getGym());
               if((u.getProfilepic()!=null)&&(!u.getProfilepic().isEmpty()))
                   Picasso.get().load(u.getProfilepic()).into(profileImageView);
            }
        });
    }
}