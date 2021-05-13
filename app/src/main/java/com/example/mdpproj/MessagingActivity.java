package com.example.mdpproj;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagingActivity extends AppCompatActivity {
    CircleImageView profilePic, sendButton;
    TextView userName;
    EditText content;
    String SenderUid = FirebaseAuth.getInstance().getCurrentUser().getUid(), ReceiverUid;
    DatabaseReference MessageDb = FirebaseDatabase.getInstance().getReference().child("Messages");
    FirebaseRecyclerAdapter<MessageObject, com.example.mdpproj.MessageViewHolder> adapter;
    RecyclerView messageList;
    MessagingAdapter messageAdapter;
    DatabaseReference userRef, messageRef, chatReference;
    ArrayList<MessageObject> messageArrayList = new ArrayList<MessageObject>();
    String SenderRoom, ReceiverRoom;
    public static String sImage, rImage;

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messageList.setLayoutManager(linearLayoutManager);
        messageAdapter = new MessagingAdapter(messageArrayList,this);
        messageList.setAdapter(messageAdapter);

        Intent intent = getIntent();
        String u = intent.getStringExtra("userName");
        String p = intent.getStringExtra("ProfilePic");
        ReceiverUid = intent.getStringExtra("Uid");

        SenderRoom = SenderUid + ReceiverUid;
        ReceiverRoom = ReceiverUid + SenderUid;

        userName.setText(u);

        if(p !=null && !p.equals("default"))
            Picasso.get().load(Uri.parse(p)).into(profilePic);

       /* messageList.setLayoutManager(new LinearLayoutManager(this));
        messageList.setAdapter(adapter);*/


        userRef = FirebaseDatabase.getInstance().getReference("UserObject").child(SenderUid);
        messageRef =  FirebaseDatabase.getInstance().getReference("Chats");
        chatReference = FirebaseDatabase.getInstance().getReference("Chats").child(SenderRoom).child("messages");

        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               messageArrayList.clear();

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    MessageObject messages =  dataSnapshot.getValue(MessageObject.class);
                    messageArrayList.add(messages);
                    Log.i("Messages",messages.toString());
                    messageAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("profilepic")!= null)
                    sImage = snapshot.child("profilepic").getValue().toString();
                rImage = p;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = content.getText().toString().trim();
                if(message.equals("")){
                    Toast.makeText(getApplicationContext(),"Cant Send An Empty Message",Toast.LENGTH_SHORT).show();
                }else{
                    MessageObject m = new MessageObject(1,message,SenderUid);
                    //MessageDb.child(ReceiverUid).push().setValue(m);
                    m.setViewType(1);
                    messageRef.child(SenderRoom).child("messages").push().setValue(m).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            m.setViewType(2);
                            messageRef.child(ReceiverRoom).child("messages").push().setValue(m).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(),"Message Uploaded to Firebase",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                }
                content.setText("");
            }
        });

    }

  /*  @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }*/
}
