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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class FindBuddiesFragment extends Fragment implements BuddyAdapter.OnClick {
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
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
        mAdapter = new BuddyAdapter(getContext(),getDataSet(), this::OnClick);
        mRecyclerView.setAdapter(mAdapter);

        mInput = (EditText) view.findViewById(R.id.input);
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
        DatabaseReference usersDb = FirebaseDatabase.getInstance().getReference().child("users");
        Query query;
      /*  if(mInput.getText() != null)
           query = usersDb.orderByChild("userName").startAt(mInput.getText().toString()).endAt(mInput.getText().toString() + "\uf8ff");
*/
        usersDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String user_name = "";
                String profileUrl = "";
                String age = "";
                String uid = snapshot.getRef().getKey();
                if(snapshot.child("userName").getValue() != null){
                    user_name = snapshot.child("userName").getValue().toString();
                }
                if(snapshot.child("age").getValue() != null){
                    age = snapshot.child("age").getValue().toString();
                }
                if(snapshot.child("profilepic").getValue() != null){
                    profileUrl = snapshot.child("profilepic").getValue().toString();
                }
                if(!uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    BuddiesObject object = new BuddiesObject(user_name,age,profileUrl,uid);
                    results.add(object);
                   // mRecyclerView.getRecycledViewPool().clear();
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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

    private ArrayList<BuddiesObject> results = new ArrayList<>();
    private ArrayList<BuddiesObject> getDataSet() {
        listenForData();
        for(int i = 0; i < results.size();i++){
            Log.i("Results", results.get(i).getUser_name());
        }
        return results;
    }

    public void OnClick(int position) {
        results.get(position);
        Intent intent = new Intent(getContext(), buddiesProf.class);
        intent.putExtra("name",results.get(position).getUid());
        startActivity(intent);
    }
}
