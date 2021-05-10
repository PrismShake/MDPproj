package com.example.mdpproj;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class messagePageOne extends AppCompatActivity implements buddiesAdapter.OnClick{

    RecyclerView recyclerView;
    DatabaseReference database;
    buddiesAdapter myAdapter;
    ArrayList<Users> list;
    String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_message_page_one);

        recyclerView = findViewById(R.id.matchedList);
        database = FirebaseDatabase.getInstance().getReference("Users");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new buddiesAdapter(this,list,this);
        recyclerView.setAdapter(myAdapter);


        database.child(current_user_id).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Users u = dataSnapshot.getValue(Users.class);
                List<String> finalBuddies = new ArrayList<>();
                if(u!=null){
                    finalBuddies = u.getBuddies();
                }

                List<String> finalBuddies1 = finalBuddies;
                database.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()){
                            Users u = data.getValue(Users.class);
                            if(u!=null) {
                                String Uid = u.getmUid();
                                if(Uid!=null && !Uid.equals(current_user_id)) {
                                    if(finalBuddies1.contains(Uid)){
                                        Users user = data.getValue ( Users.class );
                                        list.add ( user );
                                    }
                                }
                            }

                        }
                        myAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
      /*  database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Users u = dataSnapshot.getValue(Users.class);
                    String Uid = u.getmUid();
                    
                    if(Uid != null)
                        Log.i("Snapshot",Uid);
                    Log.i("Snapshot",current_user_id);
                    if(Uid!=null && !Uid.equals(current_user_id)) {
                        Users user = dataSnapshot.getValue ( Users.class );
                        list.add ( user );
                    }
                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/


    }
    @Override
    public void OnClick(int position) {
        list.get(position);
        Intent intent = new Intent(this, MessageViewHolder.messagePageTwo.class);
        intent.putExtra("name",list.get(position).getmUid());
        startActivity(intent);
    }

}