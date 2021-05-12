package com.example.mdpproj;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagingActivity extends AppCompatActivity {
    CircleImageView profilePic;
    TextView userName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_message_page_two);

        profilePic = findViewById(R.id.displayPic);
        userName = findViewById(R.id.displayName);

        Intent intent = getIntent();
        String u = intent.getStringExtra("userName");
        String p = intent.getStringExtra("ProfilePic");

        userName.setText(u);
        Picasso.get().load(Uri.parse(p)).into(profilePic);

    }
}
