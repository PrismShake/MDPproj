package com.example.mdpproj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class buddiesPage extends AppCompatActivity implements buddiesAdapter.OnClick {
    /*TextView fullName = findViewById(R.id.FullName);
    String name = fullName.getText ().toString ();*/
    RecyclerView recyclerView;
    DatabaseReference database;
    buddiesAdapter myAdapter;
    ArrayList<Users> list;
    String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_buddies_page);

        recyclerView = findViewById(R.id.userList);
        database = FirebaseDatabase.getInstance().getReference("Users");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new buddiesAdapter(this,list,this);
        recyclerView.setAdapter(myAdapter);


        database.addValueEventListener(new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Users u = dataSnapshot.getValue(Users.class);
                    String Uid = u.getmUid();
                    Log.i("Snapshot",Uid);
                    if(!Uid.equals(current_user_id)) {
                        Users user = dataSnapshot.getValue ( Users.class );
                        list.add ( user );
                    }
                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        }

    @Override
    public void OnClick(int position) {
        list.get(position);
        Intent intent = new Intent(this,buddiesProf.class);
        intent.putExtra("name",list.get(position).getmUid());
        startActivity(intent);
    }
}