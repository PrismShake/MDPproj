package com.example.mdpproj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.util.List;

public class ChatDisplayPageActivity extends AppCompatActivity implements buddiesAdapter.OnClick{
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    private List<BuddyObject> results = new ArrayList<>();
    String current_user = FirebaseAuth.getInstance().getCurrentUser().getUid();
    // private RecyclerView.LayoutManager mLayoutManager;
    EditText mInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.my_buddies_fragment);
        mRecyclerView = findViewById(R.id.recyclerViewMyBuddies);
        mRecyclerView.getRecycledViewPool().clear();
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new BuddyAdapter(this, getDataSet(), this::OnClick, 2);
        mRecyclerView.setAdapter(mAdapter);

//        mInput = getActivity().findViewById(R.id.input);
        Button mSearch = findViewById(R.id.search);
        mSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                clear();
                listenForData();
            }
        });

    }

    private void listenForData() {
        results.add(new BuddyObject("Henry", "18", "default", "eafesfewfewf"));
        String user_uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ArrayList<String> buddies = getBuddies();
        DatabaseReference usersDb = FirebaseDatabase.getInstance().getReference("UserObject");
        // Query query = usersDb.orderByChild("userName").startAt(mInput.getText().toString()).endAt(mInput.getText().toString() + "\uf8ff");
        usersDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String user_name = "";
                    String profileUrl = "";
                    String age = "";
                    String uid = dataSnapshot.getRef().getKey();
                    // String uid = dataSnapshot.child("uid").getValue().toString();
                    if (dataSnapshot.child("userName").getValue() != null) {
                        user_name = dataSnapshot.child("userName").getValue().toString();
                    }
                    if (dataSnapshot.child("age").getValue() != null) {
                        age = dataSnapshot.child("age").getValue().toString();
                    }
                    if (dataSnapshot.child("profilepic").getValue() != null) {
                        profileUrl = dataSnapshot.child("profilepic").getValue().toString();
                    }
                    if (!uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) && buddies.contains(uid)) {
                        BuddyObject object = new BuddyObject(user_name, age, profileUrl, uid);
                        if (!results.contains(object)) {
                            results.add(object);
                            mAdapter.notifyDataSetChanged();
                        }
                        mRecyclerView.getRecycledViewPool().clear();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void clear() {
        int size = this.results.size();
        this.results.clear();
        mRecyclerView.getRecycledViewPool().clear();
        mAdapter.notifyItemRangeChanged(0, size);
    }

    private ArrayList<String> getBuddies()
    {
        ArrayList<String> buddies = new ArrayList<String>();
        DatabaseReference buddiesList = FirebaseDatabase.getInstance().getReference("UserObject").child(current_user).child("Buddies");
        buddiesList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String uid = dataSnapshot.getRef().getKey();
                        if (uid != null) {
                            buddies.add(uid);

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return buddies;
    }

    private List<BuddyObject> getDataSet() {
        listenForData();
        for (int i = 0; i < results.size(); i++) {
            Log.i("Results", results.get(i).getUser_name());
        }
        return results;
    }

    public void OnClick(int position) {
        results.get(position);
        Intent intent = new Intent(this, GoToBuddyProfileActivity.class);
        intent.putExtra("name", results.get(position).getUid());
        startActivity(intent);
    }
//    RecyclerView recyclerView;
//    DatabaseReference database;
//    buddiesAdapter myAdapter;
//    ArrayList<UserObject> list;
//    String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
//        setContentView(R.layout.activity_message_page_one);
//
//        recyclerView = findViewById(R.id.matchedList);
//        database = FirebaseDatabase.getInstance().getReference("UserObject");
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        list = new ArrayList<>();
//        myAdapter = new buddiesAdapter(this,list,this);
//        recyclerView.setAdapter(myAdapter);
//
//
//        database.child(current_user_id).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
//            @Override
//            public void onSuccess(DataSnapshot dataSnapshot) {
//                UserObject u = dataSnapshot.getValue(UserObject.class);
//                List<String> finalBuddies = new ArrayList<>();
//                if(u!=null){
//                    finalBuddies = u.getBuddies();
//                }
//
//                List<String> finalBuddies1 = finalBuddies;
//                database.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
//                    @Override
//                    public void onSuccess(DataSnapshot snapshot) {
//                        for (DataSnapshot data : snapshot.getChildren()){
//                            UserObject u = data.getValue(UserObject.class);
//                            if(u!=null) {
//                                String Uid = u.getmUid();
//                                if(Uid!=null && !Uid.equals(current_user_id)) {
//                                    if(finalBuddies1.contains(Uid)){
//                                        UserObject user = data.getValue ( UserObject.class );
//                                        list.add ( user );
//                                    }
//                                }
//                            }
//
//                        }
//                        myAdapter.notifyDataSetChanged();
//                    }
//                });
//            }
//        });
      /*  database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserObject u = dataSnapshot.getValue(UserObject.class);
                    String Uid = u.getmUid();
                    
                    if(Uid != null)
                        Log.i("Snapshot",Uid);
                    Log.i("Snapshot",current_user_id);
                    if(Uid!=null && !Uid.equals(current_user_id)) {
                        UserObject user = dataSnapshot.getValue ( UserObject.class );
                        list.add ( user );
                    }
                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/


//    }
//    @Override
//    public void OnClick(int position) {
//        list.get(position);
//        Intent intent = new Intent(this, MessageViewHolder.messagePageTwo.class);
//        intent.putExtra("name",list.get(position).getmUid());
//        startActivity(intent);
//    }

}