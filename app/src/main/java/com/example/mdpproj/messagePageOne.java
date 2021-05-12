package com.example.mdpproj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class messagePageOne extends AppCompatActivity implements buddiesAdapter.OnClick{
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    private List<BuddiesObject> results = new ArrayList<>();
    String current_user = FirebaseAuth.getInstance().getCurrentUser().getUid();
    // private RecyclerView.LayoutManager mLayoutManager;
    EditText mInput;



    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_buddies_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerViewMyBuddies);
        mRecyclerView.getRecycledViewPool().clear();
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new BuddyAdapter(this, getDataSet(), this::OnClick, 2);
        mRecyclerView.setAdapter(mAdapter);

//        mInput = getActivity().findViewById(R.id.input);
        Button mSearch = view.findViewById(R.id.search);
        mSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                clear();
                listenForData();
            }
        });


        return view;

    }

    private void listenForData() {
        results.add(new BuddiesObject("Henry", "18", "default", "eafesfewfewf"));
        String user_uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ArrayList<String> buddies = getBuddies();
        DatabaseReference usersDb = FirebaseDatabase.getInstance().getReference("Users");
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
                        BuddiesObject object = new BuddiesObject(user_name, age, profileUrl, uid);
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
        DatabaseReference buddiesList = FirebaseDatabase.getInstance().getReference("Users").child(current_user).child("Buddies");
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

    private List<BuddiesObject> getDataSet() {
        listenForData();
        for (int i = 0; i < results.size(); i++) {
            Log.i("Results", results.get(i).getUser_name());
        }
        return results;
    }

    public void OnClick(int position) {
        results.get(position);
        Intent intent = new Intent(this, buddiesProf.class);
        intent.putExtra("name", results.get(position).getUid());
        startActivity(intent);
    }
//    RecyclerView recyclerView;
//    DatabaseReference database;
//    buddiesAdapter myAdapter;
//    ArrayList<Users> list;
//    String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
//        setContentView(R.layout.activity_message_page_one);
//
//        recyclerView = findViewById(R.id.matchedList);
//        database = FirebaseDatabase.getInstance().getReference("Users");
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
//                Users u = dataSnapshot.getValue(Users.class);
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
//                            Users u = data.getValue(Users.class);
//                            if(u!=null) {
//                                String Uid = u.getmUid();
//                                if(Uid!=null && !Uid.equals(current_user_id)) {
//                                    if(finalBuddies1.contains(Uid)){
//                                        Users user = data.getValue ( Users.class );
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


//    }
//    @Override
//    public void OnClick(int position) {
//        list.get(position);
//        Intent intent = new Intent(this, MessageViewHolder.messagePageTwo.class);
//        intent.putExtra("name",list.get(position).getmUid());
//        startActivity(intent);
//    }

}