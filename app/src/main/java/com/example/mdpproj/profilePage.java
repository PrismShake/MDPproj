package com.example.mdpproj;

import android.app.PendingIntent;
import android.os.Bundle;
import android.util.Log;
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
    EditText fullName, age, pronouns;
    DatabaseReference mRoot;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        getSupportActionBar().hide();
        setContentView ( R.layout.activity_profile_page );
        uname = findViewById(R.id.uName);
        fullName = findViewById(R.id.FullName);
        age = findViewById(R.id.Age);
        pronouns = findViewById(R.id.Pronouns);

        //I get the current user
        String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //I obtain the User node from the Firebase database
        mRoot = FirebaseDatabase.getInstance().getReference("Users");

        //where data is read from database and used to update the profile accordingly
        //based off the users response to the questionaire
        mRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //I go through each user in the node
                for(DataSnapshot user: snapshot.getChildren()){
                    Log.i("Snapshot",user.getValue().toString());
                    //I get the User
                    Users u = user.getValue(Users.class);
                    //I use a getter method from the Users.class
                    String Uid = u.getmUid();
                    Log.i("Snapshot",Uid);
                    //I get the current user
                    String current_user = auth.getInstance().getCurrentUser().getUid();
                    //if the user in the User Node is the current user I take their info and update the profile
                    if(Uid.equals(current_user)) {
                        String user_name = u.getUserName();
                        uname.setText(user_name);
                        fullName.setText(u.getFull_name());
                        age.setText(u.getAge());
                        pronouns.setText(u.getPronouns());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}