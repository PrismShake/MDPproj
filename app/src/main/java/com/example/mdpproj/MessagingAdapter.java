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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagingAdapter extends RecyclerView.Adapter{

    Context context;
    ArrayList<MessageObject> messageObjectArrayList;
    int SEND = 1, RECEIVE = 2;

    public MessagingAdapter(ArrayList<MessageObject> messageList, Context context){
        this.messageObjectArrayList = messageList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        MessageObject messages = messageObjectArrayList.get(position);
        if(messageObjectArrayList.get(position).getViewType() == 1){
            return SEND;
        }else{
            return RECEIVE;
        }

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SEND){
            View v = LayoutInflater.from(context).inflate(R.layout.item_sender, parent, false);
            return new SenderViewHolder(v);
        }else{
            View v = LayoutInflater.from(context).inflate(R.layout.item_receiver, parent, false);
            return new ReceiverViewHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageObject message = messageObjectArrayList.get(position);
        if(messageObjectArrayList.get(position).getViewType() == SEND){
            SenderViewHolder viewHolder = (SenderViewHolder) holder;
            viewHolder.message.setText(message.getmContent());
            if(MessagingActivity.sImage!=null && !MessagingActivity.sImage.equals("default"))
                Picasso.get().load(MessagingActivity.sImage).into(((SenderViewHolder) holder).profile_sender);

        }else{
            ReceiverViewHolder viewHolder = (ReceiverViewHolder) holder;
            viewHolder.message.setText(message.getmContent());
            if(MessagingActivity.rImage!=null && !MessagingActivity.rImage.equals("default"))
                Picasso.get().load(MessagingActivity.rImage).into(((ReceiverViewHolder) holder).profile_receiver);
        }
    }

    @Override
    public int getItemCount() {
        return messageObjectArrayList.size();
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
