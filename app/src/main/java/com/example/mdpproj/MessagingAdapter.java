package com.example.mdpproj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagingAdapter extends RecyclerView.Adapter{

    Context context;
    ArrayList<MessageObject> messageObjectArrayList;
    int SEND = 1, RECEIVE = 2;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SEND){
            View v = LayoutInflater.from(context).inflate(R.layout.item_sender, parent, false);
            return new SenderViewHolder(v);
        }else{
            View v = LayoutInflater.from(context).inflate(R.layout.item_sender, parent, false);
            return new ReceiverViewHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageObject message = messageObjectArrayList.get(position);
        if(messageObjectArrayList.get(position).getViewType() == SEND){
            SenderViewHolder viewHolder = (SenderViewHolder) holder;
            viewHolder.message.setText(message.getmContent());
            //Piccaso
        }else{
            ReceiverViewHolder viewHolder = (ReceiverViewHolder) holder;
            viewHolder.message.setText(message.getmContent());
            //Picasso
        }
    }

    @Override
    public int getItemCount() {
        return messageObjectArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        MessageObject messages = messageObjectArrayList.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getUid())){
            return SEND;
        }else{
            return RECEIVE;
        }

    }

    public static class ReceiverViewHolder extends RecyclerView.ViewHolder{

        CircleImageView profile_receiver;
        TextView message;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_receiver = itemView.findViewById(R.id.receiver_pfp);
            message = itemView.findViewById(R.id.receiver_message);
        }
    }


    public static class SenderViewHolder extends RecyclerView.ViewHolder{

        CircleImageView profile_sender;
        TextView message;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_sender = itemView.findViewById(R.id.sender_pfp);
            message = itemView.findViewById(R.id.sender_message);
        }
    }
}
