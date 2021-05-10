package com.example.mdpproj;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Edit_Profile_Page extends AppCompatActivity {

    private CircleImageView profileImageView;
    private Button closeButton,saveButton;
    private TextView profileChangeBtn;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private Uri imageUri;
    private String myUri = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePicsRef;
    String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile__page);

        profileChangeBtn = findViewById(R.id.change_profile_btn);
        profileImageView = findViewById(R.id.profile_image);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        storageProfilePicsRef = FirebaseStorage.getInstance().getReference().child("Profile Pic");

        closeButton = findViewById(R.id.btn_Close);
        saveButton = findViewById(R.id.btn_Save);

        closeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(Edit_Profile_Page.this, profilePage.class));
            }
        });
        
        saveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                uploadProfilePic();
            }
        });

        profileChangeBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                CropImage.activity().setAspectRatio(1,1).start(Edit_Profile_Page.this);
            }
        });

        getUserInfo();

    }

  private void getUserInfo() {
        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profileImageView.setImageURI(imageUri);
        }
        else{
            Toast.makeText(this,"Error, Try again!", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadProfilePic() {
       if(imageUri!=null){
           final StorageReference fileRef = storageProfilePicsRef
                   .child(mAuth.getCurrentUser().getUid() + ".jpg");
           uploadTask = fileRef.putFile(imageUri);
           uploadTask.continueWithTask(new Continuation() {
               @Override
               public Object then(@NonNull Task task) throws Exception {
                   if(!task.isSuccessful()){
                       throw task.getException();
                   }
                   return fileRef.getDownloadUrl();
               }
           }).addOnCompleteListener(new OnCompleteListener() {
               @Override
               public void onComplete(@NonNull Task task) {
                   if(task.isSuccessful()){
                       Uri downloadUrl = (Uri) task.getResult();
                       myUri = downloadUrl.toString();

                       HashMap<String, Object> userMap = new HashMap<>();
                       userMap.put("image",myUri);
                       databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);

                   }
               }
           });
       }else{
           Toast.makeText(this,"Image not selected",Toast.LENGTH_SHORT).show();
       }
    }
}