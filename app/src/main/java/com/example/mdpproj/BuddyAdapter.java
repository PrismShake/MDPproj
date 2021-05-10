package com.example.mdpproj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BuddyAdapter extends RecyclerView.Adapter<BuddyAdapter.MyViewHolder> {

    Context context;
    List<BuddiesObject> list;
    private OnClick onclick;

    public BuddyAdapter(Context context, List<BuddiesObject> list, OnClick onclick) {
        this.list = list;
        this.onclick = onclick;
        this.context = context;
    }

    @NonNull
    @Override
    public BuddyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.buddies_item, parent, false);
        return new MyViewHolder(v, onclick);
    }

    @Override //
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        BuddiesObject user = list.get(position);
        holder.userName.setText(user.getUser_name());
//            holder.lastName.setText(user.getLastName());
        holder.age.setText(user.getAge());
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

        public MyViewHolder(@NonNull View itemView, OnClick onclick) {
            super(itemView);

            userName = itemView.findViewById(R.id.userName);
//                lastName = itemView.findViewById(R.id.tvlastName);
            age = itemView.findViewById(R.id.age);
            this.onclick = onclick;
            itemView.setOnClickListener(this);


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