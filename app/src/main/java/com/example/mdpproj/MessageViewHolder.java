package com.example.mdpproj;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageViewHolder extends RecyclerView.ViewHolder {
    TextView messageTV;
    TextView messengerTV;
    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        messageTV = itemView.findViewById(R.id.messageTextView);
        messengerTV = itemView.findViewById(R.id.messengerTextView);
    }
    public void bindMessage(Message message){
        //set the two textviews
        if(message.getText() != null){
            messageTV.setText(message.getText());
        }
        messengerTV.setText(message.getUid());
    }
}
