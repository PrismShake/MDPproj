package com.example.mdpproj;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class BuddyAdapter extends RecyclerView.Adapter<BuddyAdapter.MyViewHolder> {

    Context context;
    List<BuddiesObject> list;
    Boolean myBuddy;
    private OnClick onclick;
    ArrayList<String> buddies;
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public BuddyAdapter(Context context, List<BuddiesObject> list, OnClick onclick, boolean myBuddy) {
        this.list = list;
        this.onclick = onclick;
        this.context = context;
        this.myBuddy = myBuddy;
        //get buddies
        buddies = new ArrayList<String>();
        DatabaseReference buddiesList = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Buddies");
        buddiesList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        String uid = dataSnapshot.getRef().getKey();
                        if(uid != null){
                            buddies.add(uid);

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @NonNull
    @Override
    public BuddyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.buddies_item, parent, false);
        return new MyViewHolder(v, onclick);
    }

    @Override //
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //get buddies
      //  String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
   /*     ArrayList<String> buddies = new ArrayList<String>();
        DatabaseReference buddiesList = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Buddies");
        buddiesList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        String uid = dataSnapshot.getRef().getKey();
                        if(uid != null){
                            buddies.add(uid);

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/


        if(buddies.contains(list.get(position).getUid())) {
            holder.match_with_buddy.setTag("unmatch");
            holder.match_with_buddy.setImageResource(R.drawable.ic_unmatch);
        }else{
            holder.match_with_buddy.setTag("match");
            holder.match_with_buddy.setImageResource(R.drawable.ic_match);
        }

        BuddiesObject user = list.get(position);
        holder.userName.setText(user.getUser_name());
//            holder.lastName.setText(user.getLastName());
        holder.age.setText(user.getAge());
        holder.go_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(position);
                Intent intent = new Intent(context, buddiesProf.class);
                intent.putExtra("name",list.get(position).getUid());
                context.startActivity(intent);
            }
        });
        holder.match_with_buddy.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                if(!myBuddy){
                    if (holder.match_with_buddy.getTag().equals("match")) {
                        holder.match_with_buddy.setTag("unmatch");
                        holder.match_with_buddy.setImageResource(R.drawable.ic_unmatch);
                       // holder.match_with_buddy.setBackgroundResource(R.drawable.ic_unmatch);
                        holder.match_with_buddy.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFA500")));
                        FirebaseDatabase.getInstance().getReference()
                                .child("Users").child(userId).child("Buddies").child(list.get(position).getUid()).setValue(true);
                        notifyDataSetChanged();

                    } else {
                        holder.match_with_buddy.setTag("match");
                        holder.match_with_buddy.setImageResource(R.drawable.ic_match);
                        holder.match_with_buddy.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFA500")));
                        FirebaseDatabase.getInstance().getReference()
                                .child("Users").child(userId).child("Buddies").child(list.get(position).getUid()).removeValue();
                       /* list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, list.size());*/
                    }
                }else{
                    holder.match_with_buddy.setBackgroundResource(R.drawable.ic_unmatch);
                    holder.match_with_buddy.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFA500")));
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users").child(userId).child("Buddies").child(list.get(position).getUid()).removeValue();
                   list.remove(position);
                   notifyItemRemoved(position);
                   notifyItemRangeChanged(position, list.size());

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<BuddiesObject> getUserList() {
        return this.list;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView userName,age;
        OnClick onclick;
        Button go_to_profile;
        ImageView match_with_buddy;

        public MyViewHolder(@NonNull View itemView, OnClick onclick) {
            super(itemView);

            userName = itemView.findViewById(R.id.userName);
//                lastName = itemView.findViewById(R.id.tvlastName);
            age = itemView.findViewById(R.id.age);
            go_to_profile = itemView.findViewById(R.id.go_to_profile);
            match_with_buddy = itemView.findViewById(R.id.match_with_buddy);
            /*
            this.onclick = onclick;
            itemView.setOnClickListener(this);*/


        }

        @Override
        public void onClick(View view) {
            onclick.OnClick(getAdapterPosition());
        }
    }

    public interface OnClick {
        void OnClick(int position);
    }
}