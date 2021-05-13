package com.example.mdpproj;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
    DatabaseReference MessageDb = FirebaseDatabase.getInstance().getReference().child("Messages");
    FirebaseRecyclerAdapter<MessageObject, com.example.mdpproj.MessageViewHolder> adapter;
    RecyclerView messageList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_message_page_two);

        profilePic = findViewById(R.id.displayPic);
        userName = findViewById(R.id.displayName);
        sendButton = findViewById(R.id.send_message);
        content = findViewById(R.id.message_to_send);
        messageList = findViewById(R.id.messageList);

        Intent intent = getIntent();
        String u = intent.getStringExtra("userName");
        String p = intent.getStringExtra("ProfilePic");
        String uid = intent.getStringExtra("Uid");

        userName.setText(u);

        messageList.setLayoutManager(new LinearLayoutManager(this));
        messageList.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = content.getText().toString().trim();
                if(message.equals("")){
                    Toast.makeText(getApplicationContext(),"Cant Send An Empty Message",Toast.LENGTH_SHORT).show();
                }else{
                    MessageObject m = new MessageObject(1,message,current_id);
                    MessageDb.child(uid).push().setValue(m);
                    content.setText("");
                    m.setText("");
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
