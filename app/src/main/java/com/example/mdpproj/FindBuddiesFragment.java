package com.example.mdpproj;

import android.content.Context;
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
import androidx.fragment.app.Fragment;
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

public class FindBuddiesFragment extends Fragment implements BuddyAdapter.OnClick {
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    private List<BuddyObject> results = new ArrayList<>();
   // private RecyclerView.LayoutManager mLayoutManager;
    EditText mInput;

    public static  FindBuddiesFragment getInstance(){
        FindBuddiesFragment fragment = new FindBuddiesFragment();
        return fragment;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.find_buddies_fragment,container,false);

        mRecyclerView = view.findViewById(R.id.recyclerViewFindBuddies);
        mRecyclerView.getRecycledViewPool().clear();
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new BuddyAdapter(getContext(),getDataSet(), this::OnClick,0);
        mRecyclerView.setAdapter(mAdapter);

        mInput = getActivity().findViewById(R.id.input);
        Button mSearch = view.findViewById(R.id.search);
        mSearch.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                clear();
                listenForData();
            }
        });


        return view;

    }

    private void listenForData() {
        results.add(new BuddyObject("Henry","18","default","eafesfewfewf"));
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
                    if (!uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        BuddyObject object = new BuddyObject(user_name, age, profileUrl, uid);
                        if(!results.contains(object)) {
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
        mAdapter.notifyItemRangeChanged(0,size);
    }


    private List<BuddyObject> getDataSet() {
        listenForData();
        for(int i = 0; i < results.size();i++){
            Log.i("Results", results.get(i).getUser_name());
        }
        return results;
    }

    public void OnClick(int position) {
        results.get(position);
        Intent intent = new Intent(getContext(), GoToBuddyProfileActivity.class);
        intent.putExtra("name",results.get(position).getUid());
        startActivity(intent);
    }
}
