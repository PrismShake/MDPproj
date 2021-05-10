package com.example.mdpproj;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class pickworks extends AppCompatActivity {
    DatabaseReference mRoot;
    FirebaseAuth auth;
    FirebaseStorage storage;
    StorageReference storageReference;
    String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    ImageView bench, free, tred, bike, yoga, res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_pickworks );

/*        res = (ImageView)findViewById ( R.id.res ) ;
        tred= (ImageView)findViewById ( R.id.tread );
         bike= (ImageView)findViewById ( R.id.bike );
        yoga= ( ImageView)findViewById ( R.id.yog );
        bench = (ImageView)findViewById ( R.id.bench );
        free = (ImageView)findViewById ( R.id.bell ) ;
        mRoot = FirebaseDatabase.getInstance().getReference("Users");*/

        //where data is read from database and used to update the profile accordingly
        //based off the users response to the questionaire

        //listens to whether get() is successful or not

    }
  /*  public void resistanceMach(View view) {
        mRoot.child(current_user_id).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot> () {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override

            public void onSuccess(DataSnapshot dataSnapshot) {
                Users u = dataSnapshot.getValue(Users.class);

                u.setOne ( res );

            }
        });
    }

    public void benchPress(View view) {
        mRoot.child(current_user_id).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot> () {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override

            public void onSuccess(DataSnapshot dataSnapshot) {
                Users u = dataSnapshot.getValue(Users.class);

                u.setTwo ( bench );

            }
        });
    }

    public void yoga(View view) {
        mRoot.child(current_user_id).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot> () {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override

            public void onSuccess(DataSnapshot dataSnapshot) {
                Users u = dataSnapshot.getValue(Users.class);

                u.setThree ( yoga );

            }
        });
    }

    public void freeWeights(View view) {
        mRoot.child(current_user_id).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot> () {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override

            public void onSuccess(DataSnapshot dataSnapshot) {
                Users u = dataSnapshot.getValue(Users.class);

                u.setFour ( free );

            }
        });
    }

    public void treadmill(View view) {
        mRoot.child(current_user_id).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot> () {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override

            public void onSuccess(DataSnapshot dataSnapshot) {
                Users u = dataSnapshot.getValue(Users.class);

                u.setOne ( tred );

            }
        });
    }

    public void cycling(View view) {
        mRoot.child(current_user_id).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot> () {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Users u = dataSnapshot.getValue(Users.class);

                u.setTwo ( bike );

            }
        });
    }*/

}