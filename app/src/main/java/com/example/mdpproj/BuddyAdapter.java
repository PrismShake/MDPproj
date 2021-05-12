package com.example.mdpproj;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BuddyAdapter extends RecyclerView.Adapter<BuddyAdapter.MyViewHolder> {


    Context context;
    List<BuddyObject> list;
    int typeOfDisplay;
    private OnClick onclick;
    ArrayList<String> buddies;
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    //passes context aka the activity, the list of buddies to display, onclick, typeOfDisplay
          /*
          When user clicks on right button does this depending on typeOfDisplay
          Remember
          0 --> FindBuddiesFragment
          1 --> MyBuddiesFragment
          2 --> Message
         */
    public BuddyAdapter(Context context, List<BuddyObject> list, OnClick onclick, int typeOfDisplay) {
        this.list = list;
        this.onclick = onclick;
        this.context = context;
        this.typeOfDisplay = typeOfDisplay;

        //get buddies of current user from Firebase
        buddies = new ArrayList<String>();
        DatabaseReference buddiesList = FirebaseDatabase.getInstance().getReference("UserObject").child(userId).child("Buddies");
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override //
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      /*
          When user clicks on right button does this depending on typeOfDisplay
          Remember
          0 --> FindBuddiesFragment
          1 --> MyBuddiesFragment
          2 --> Message
         */

        /*
            This section of the code sets the display before the user clicks on the buttons
         */
        //if display is the FindBuddies Fragment do this
        if(typeOfDisplay == 0) {
            //if the user is a buddy option to unmatch
            if (buddies.contains(list.get(position).getUid())) {
                holder.match_with_buddy.setTag("unmatch");
                holder.match_with_buddy.setImageResource(R.drawable.ic_unmatch);
                //if the user is not a buddy set option to match
            } else {
                holder.match_with_buddy.setTag("match");
                holder.match_with_buddy.setImageResource(R.drawable.ic_match);
            }
            //if display is the MyBuddiesFragment do this
        }else if(typeOfDisplay == 1){
            //dont need to toggle can simply unmatch
            holder.match_with_buddy.setTag("unmatch");
            holder.match_with_buddy.setImageResource(R.drawable.ic_unmatch);
            holder.match_with_buddy.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFA500")));
            //if display is the Message Activity do this
        }else{
            //set icon to message
            holder.match_with_buddy.setTag("message");
            holder.match_with_buddy.setImageResource(R.drawable.ic_message);
            holder.match_with_buddy.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFA500")));
        }

        //set buddy's username and age from list of buddyObject
        BuddyObject buddy = list.get(position);
        holder.userName.setText(buddy.getUser_name());
        holder.age.setText(buddy.getAge());
        if(list.get(position).getProfileUrl() != null || !list.get(position).getProfileUrl().equals("default"))
            Picasso.get().load(Uri.parse(list.get(position).getProfileUrl())).into(holder.profile_pic);
        else
            holder.profile_pic.setImageResource(R.drawable.profile_icon);

        /*
            This section of the code is where the user clicks on the left and right button
         */


        //When user clicks on the left button goes to profile
        holder.go_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(position);
                Intent intent = new Intent(context, GoToBuddyProfileActivity.class);
                intent.putExtra("name",list.get(position).getUid());
                context.startActivity(intent);
            }
        });

        /*
          When user clicks on right button does this depending on typeOfDisplay
          Remember
          0 --> FindBuddiesFragment
          1 --> MyBuddiesFragment
          2 --> Message
         */
        holder.match_with_buddy.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                /*
                   When user is at the FindBuddiesFragment, user can toggle between match and unmatching the users listed
                   to add/remove from their buddies page
                 */
                if(typeOfDisplay == 0){
                    if (holder.match_with_buddy.getTag().equals("match")) {
                        //add buddy to firebase
                        //toggle to unmatch b/c you removed the user from buddy's list so then want option to match back with them
                        holder.match_with_buddy.setTag("unmatch");
                        holder.match_with_buddy.setImageResource(R.drawable.ic_unmatch);
                        holder.match_with_buddy.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFA500")));
                        FirebaseDatabase.getInstance().getReference()
                                .child("UserObject").child(userId).child("Buddies").child(list.get(position).getUid()).setValue(true);
                        notifyDataSetChanged();
                        return;

                    } else {
                        //remove buddy from firebase
                        //toggle to match b/c you removed the user from buddy's list so then want option to match back with them
                        holder.match_with_buddy.setTag("match");
                        holder.match_with_buddy.setImageResource(R.drawable.ic_match);
                        holder.match_with_buddy.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFA500")));
                        FirebaseDatabase.getInstance().getReference()
                                .child("UserObject").child(userId).child("Buddies").child(list.get(position).getUid()).removeValue();
                        notifyDataSetChanged();
                        return;
                    }
                    /*
                        When user is at the MyBuddiesFragment, user can only unmatch with the users listed b/c the users listed is their buddy
                        and this time if they unmatch, it removes it from the display
                     */
                }else if(typeOfDisplay == 1){
                    //remove buddy from firebase
                    FirebaseDatabase.getInstance().getReference()
                            .child("UserObject").child(userId).child("Buddies").child(list.get(position).getUid()).removeValue();
                    notifyDataSetChanged();
                    //remove buddy from recyclerview
                    list.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, list.size());
                    /*
                       When user is at the Message activity, the app goes to the messages of the user displayed who is the
                       users buddy that they clicked
                     */
                }else{
                    Intent intent = new Intent(context,MessagingActivity.class);
                    intent.putExtra("userName",list.get(position).getUser_name());
                    intent.putExtra("ProfilePic",list.get(position).getProfileUrl());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<BuddyObject> getUserList() {
        return this.list;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView userName,age;
        OnClick onclick;
        Button go_to_profile;
        ImageView match_with_buddy;
        CircleImageView profile_pic;

        public MyViewHolder(@NonNull View itemView, OnClick onclick) {
            super(itemView);


            userName = itemView.findViewById(R.id.userName);
//                lastName = itemView.findViewById(R.id.tvlastName);
            age = itemView.findViewById(R.id.age);
            go_to_profile = itemView.findViewById(R.id.go_to_profile);
            match_with_buddy = itemView.findViewById(R.id.match_with_buddy);
            profile_pic = itemView.findViewById(R.id.buddy_profile_pic);
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