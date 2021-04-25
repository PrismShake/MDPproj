package com.example.mdpproj;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/*
    Desc: Corresponds to the profilePage activity
 */
public class profilePage extends AppCompatActivity {

    TextView uname;
    EditText fullName, age, pronouns, state, city, workout, motivation, gym;
    DatabaseReference mRoot;
    FirebaseAuth auth;
    Button save;
    //I get the current user
    String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile_page);

        save = findViewById(R.id.save_profile);
        uname = findViewById(R.id.uName);
        fullName = findViewById(R.id.FullName);
        age = findViewById(R.id.Age);
        pronouns = findViewById(R.id.Pronouns);
        state = findViewById(R.id.State);
        city = findViewById(R.id.City);
        workout = findViewById(R.id.Workout);
        motivation = findViewById(R.id.Motivation);
        gym = findViewById(R.id.Gym);

        toggleEditTextAvailability(false);

        //I obtain the User node from the Firebase database
        mRoot = FirebaseDatabase.getInstance().getReference("Users");

        //where data is read from database and used to update the profile accordingly
        //based off the users response to the questionaire
        mRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //I go through each user in the node
                for(DataSnapshot user: snapshot.getChildren()){
                    //Log.i("Snapshot",user.getValue().toString());
                    Users u = user.getValue(Users.class);
                    //I use a getter method from the Users.class
                    String Uid = u.getmUid();
                    Log.i("Snapshot",Uid);
                    //if the user in the User Node is the current user I take their info and update the profile
                    if(Uid.equals(current_user_id)) {  //
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

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void editProfile(View view) {
        toggleEditTextAvailability(true);
        save.setVisibility(View.VISIBLE);
    }

    //String userName, String mUid, String full_name, String age, String pronouns, String state, String city, String gym, String workout, String motivation
    public void updateProfile(View view) {

        mRoot.child(current_user_id).setValue(new Users(uname.getText().toString(), current_user_id, fullName.getText().toString(), age.getText().toString(),pronouns.getText().toString(),
                state.getText().toString(),city.getText().toString(),gym.getText().toString(),workout.getText().toString(),motivation.getText().toString()));
        toggleEditTextAvailability(false);
        save.setVisibility(View.INVISIBLE);
    }

    public void toggleEditTextAvailability(boolean b){
        fullName.setFocusableInTouchMode(b);
        age.setFocusableInTouchMode(b);
        pronouns.setFocusableInTouchMode(b);
        state.setFocusableInTouchMode(b);
        city.setFocusableInTouchMode(b);
        workout.setFocusableInTouchMode(b);
        motivation.setFocusableInTouchMode(b);
        gym.setFocusableInTouchMode(b);
    }
}
