package com.example.mdpproj;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MessageViewHolder extends RecyclerView.ViewHolder {
    TextView messageTV;
    TextView messengerTV;
    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        messageTV = itemView.findViewById(R.id.messageTextView);
        messengerTV = itemView.findViewById(R.id.messengerTextView);
    }
    public void bindMessage(MessageObject messageObject){
        //set the two textviews
        if(messageObject.getText() != null){
            messageTV.setText(messageObject.getText());
        }
        messengerTV.setText(messageObject.getUid());
    }

    public static class messagePageTwo extends AppCompatActivity {
        String username;
        EditText message;
        Button send;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase;
        FirebaseRecyclerAdapter<MessageObject, MessageViewHolder> adapter;
        RecyclerView messageList;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate ( savedInstanceState );
            getSupportActionBar().hide();
            setContentView ( R.layout.activity_message_page_two );
            send = findViewById(R.id.send_message);
            message = findViewById(R.id.message_to_send);
            mDatabase = FirebaseDatabase.getInstance().getReference();
            messageList = findViewById(R.id.messageList);
            //initialize FirebaseRecyclerAdapter
            FirebaseRecyclerOptions<MessageObject> options =
                    new FirebaseRecyclerOptions.Builder<MessageObject>()
                        .setQuery(FirebaseDatabase.getInstance()
                                  .getReference().child("messages"), MessageObject.class)
                        .build();

            adapter = new FirebaseRecyclerAdapter<MessageObject, MessageViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull MessageObject model) {
                    //set message text
                    //set messsenger info
                    holder.bindMessage(model);
                }

                @NonNull
                @Override
                public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
                    return new MessageViewHolder(view);
                }
            };

            messageList.setLayoutManager(new LinearLayoutManager(this));
            messageList.setAdapter(adapter);
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //get context from EditText
                    String text = message.getText().toString().trim();

                    mDatabase.child("Users").child(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            Users u = dataSnapshot.getValue(Users.class);
                            if(u!=null)
                                username = u.getUserName();
                        }
                    });
                    //create MessageObject object from content
                    if(username!=null){
                        MessageObject messageObject = new MessageObject(text, user.getUid());
                        //save it to Firebase DB
                        mDatabase.child("messages").child(user.getUid()).push().setValue(messageObject);
                        messageObject.setText("");
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
}
