package com.example.mdpproj;

import android.os.Bundle;
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
    DatabaseReference mReference;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        getSupportActionBar().hide();
        setContentView ( R.layout.activity_profile_page );
        uname = findViewById(R.id.uName);

        String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mReference = FirebaseDatabase.getInstance().getReference("Users");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot user: snapshot.getChildren()){
                    String Uid = user.child("mUid").getValue(String.class);
                    if(Uid.equals(current_user_id)){
                        String user_name = user.child("userName").getValue(String.class);
                        uname.setText(user_name);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}