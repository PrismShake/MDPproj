package com.example.mdpproj;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagingActivity extends AppCompatActivity {
    CircleImageView profilePic, sendButton;
    TextView userName;
    EditText content;
    String current_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference UserDb = FirebaseDatabase.getInstance().getReference("UserObject");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_message_page_two);

        profilePic = findViewById(R.id.displayPic);
        userName = findViewById(R.id.displayName);
        sendButton = findViewById(R.id.send_message);
        content = findViewById(R.id.message_to_send);


        Intent intent = getIntent();
        String u = intent.getStringExtra("userName");
        String p = intent.getStringExtra("ProfilePic");
        String uid = intent.getStringExtra("Uid");

        userName.setText(u);
        Picasso.get().load(Uri.parse(p)).into(profilePic);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = content.getText().toString().trim();
                if(message.equals("")){
                    Toast.makeText(getApplicationContext(),"Cant Send An Empty Message",Toast.LENGTH_SHORT).show();
                }else{
                    MessageObject m = new MessageObject(message);
                    UserDb.child(uid).child("received").child(current_id).push().setValue(m);
                    content.setText("");
                }
            }
        });




    }
}
