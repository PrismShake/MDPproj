package com.example.mdpproj;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/*
    Desc: Corresponds to the profilePage activity
 */
public class profilePage extends AppCompatActivity {

    ImageView wOne, wTwo, wThree, wFour;
    private CircleImageView profileImageView;
    TextView uname;
    EditText fullName, age, pronouns, state, city, workout, motivation, gym;
    DatabaseReference mRoot;
    FirebaseAuth auth;
    Button save;
    FirebaseStorage storage;
    StorageReference storageReference;
    //I get the current user
    String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile_page);
        wOne = (ImageView)findViewById ( R.id.wB ) ;
        wTwo= (ImageView)findViewById ( R.id.wB );
        wThree = (ImageView)findViewById ( R.id.wC );
        wFour= ( ImageView)findViewById ( R.id.wc );

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        save = findViewById(R.id.save_profile);
        uname = findViewById(R.id.uName);
        fullName = findViewById(R.id.FullName);
        age = findViewById(R.id.Age);
        pronouns = findViewById(R.id.Pronouns);
        state = findViewById(R.id.State);
        city = findViewById(R.id.City);
        workout = findViewById(R.id.Workout);
        motivation = findViewById(R.id.Motivation);
        gym = findViewById(R.id.Gym);


        profileImageView = findViewById(R.id.prof);
        profileImageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        toggleEditTextAvailability(false);

        //I obtain the User node from the Firebase database
        mRoot = FirebaseDatabase.getInstance().getReference("Users");

        //where data is read from database and used to update the profile accordingly
        //based off the users response to the questionaire

        //listens to whether get() is successful or not
        mRoot.child(current_user_id).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override

            public void onSuccess(DataSnapshot dataSnapshot) {
                Users u = dataSnapshot.getValue(Users.class);
                String user_name = u.getUserName();
                uname.setText(user_name);
                fullName.setText(u.getFull_name());
                age.setText(u.getAge());
                pronouns.setText(u.getPronouns());
                city.setText(u.getCity());
                state.setText(u.getState());
                workout.setText(u.getWorkout());
                motivation.setText(u.getMotivation());
                gym.setText(u.getGym());
                wOne.setForeground ( u.getOne () );
                wTwo.setForeground ( u.getTwo () );
                wThree.setForeground ( u.getThree () );
                wFour.setForeground ( u.getFour () );
                //only be used loading local images
                if((u.getProfilepic()!=null)&&(!u.getProfilepic().isEmpty()))
                    Picasso.get().load(u.getProfilepic()).into(profileImageView);
            }
        });
/*
        //listening to all changes under particular node
        mRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //I go through each user in the node
                for(DataSnapshot user: snapshot.getChildren()){
                    //Log.i("Snapshot",user.getValue().toString());

                    Users u = user.getValue(Users.class);
                    //I use a getter method from the Users.class
                    String Uid = u.getmUid();
                    Log.i("Snapshot",Uid);
                    //if the user in the User Node is the current user I take their info and update the profile
                    if(Uid.equals(current_user_id)) {  //
                        String user_name = u.getUserName();
                        uname.setText(user_name);
                        fullName.setText(u.getFull_name());
                        age.setText(u.getAge());
                        pronouns.setText(u.getPronouns());
                        city.setText(u.getCity());
                        state.setText(u.getState());
                        workout.setText(u.getWorkout());
                        motivation.setText(u.getMotivation());
                        gym.setText(u.getGym());
                        //only be used loading local images
                        if((u.getProfilepic()!=null)&&(!u.getProfilepic().isEmpty()))
                           Picasso.get().load(u.getProfilepic()).into(profileImageView);
                        //StorageReference s  = storage.getReference().child("images/" + current_user_id + ".jpg");
                       // profileImageView.setImageURI(Uri.parse(s.getName()));

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        */

    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            profileImageView.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        final String key = auth.getInstance().getCurrentUser().getUid();
        StorageReference riversRef = storageReference.child("images/" + key);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_SHORT).show();
                        //get url from Firebase Storage
                        //riversRef.getDownloadUrl();
                        taskSnapshot.getMetadata().getReference().getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Log.i("mobdev", "Update message " + key + ": " + uri);
                                        mRoot.child(key)
                                                .child("profilepic")
                                                .setValue(uri.toString());
                                    }
                                });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Failed to upload", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getUserInfo() {
        mRoot.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.getChildrenCount() > 0){


                  if(snapshot.hasChild("image")){
                        String image = snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profileImageView);
                   }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void editProfile(View view) {
        toggleEditTextAvailability(true);
        save.setVisibility(View.VISIBLE);
    }

    //String userName, String mUid, String full_name, String age, String pronouns, String state, String city, String gym, String workout, String motivation
    public void updateProfile(View view) {

        mRoot.child(current_user_id).setValue(new Users(uname.getText().toString(), current_user_id, fullName.getText().toString(), age.getText().toString(),pronouns.getText().toString(),
                state.getText().toString(),city.getText().toString(),gym.getText().toString(),workout.getText().toString(),motivation.getText().toString(), wOne, wTwo, wThree, wFour));
        toggleEditTextAvailability(false);
        save.setVisibility(View.INVISIBLE);
    }

    public void toggleEditTextAvailability(boolean b){
        fullName.setFocusableInTouchMode(b);
        age.setFocusableInTouchMode(b);
        pronouns.setFocusableInTouchMode(b);
        state.setFocusableInTouchMode(b);
        city.setFocusableInTouchMode(b);
        workout.setFocusableInTouchMode(b);
        motivation.setFocusableInTouchMode(b);
        gym.setFocusableInTouchMode(b);
    }
}
