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

        String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mRoot = FirebaseDatabase.getInstance().getReference("Users");

        mRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot user: snapshot.getChildren()){
                    Log.i("Snapshot",user.getValue().toString());
                    Users u = user.getValue(Users.class);
                    String Uid = u.getmUid();
                    Log.i("Snapshot",Uid);
                    String current_user = auth.getInstance().getCurrentUser().getUid();
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