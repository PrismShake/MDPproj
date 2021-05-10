package com.example.mdpproj;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class buddiesMainPage extends AppCompatActivity {
    /*TextView fullName = findViewById(R.id.FullName);
    String name = fullName.getText ().toString ();*/
    TabLayout tabLayout;
    ViewPager viewPager;
    RecyclerView recyclerView;
    DatabaseReference database;
    buddiesAdapter myAdapter;
    ArrayList<Users> list;
    String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_buddies_main_page);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        getTabs();

        /*recyclerView = findViewById(R.id.userList);
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


    public void getTabs(){
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                viewPagerAdapter.addFragment(FindBuddiesFragment.getInstance(),"Find Buddies");
                viewPagerAdapter.addFragment(MyBuddiesFragment.getInstance(),"My Buddies");
                viewPager.setAdapter(viewPagerAdapter);
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }
   /* @Override
    public void OnClick(int position) {
        list.get(position);
        Intent intent = new Intent(this, buddiesProf.class);
        intent.putExtra("name",list.get(position).getmUid());
        startActivity(intent);
    }*/
}