package com.example.mdpproj;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class buddiesAdapter extends RecyclerView.Adapter<buddiesAdapter.MyViewHolder>{

        Context context;

        ArrayList<Users> list;
        private OnClick onclick;

        public buddiesAdapter(Context context, ArrayList<Users> list, OnClick onclick) {
            this.context = context;
            this.list = list;
            this.onclick = onclick;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.buddies_item,parent,false);
            return  new MyViewHolder(v,onclick);
        }

        @Override //
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            Users user = list.get(position);
            holder.firstName.setText(user.getFull_name ());
//            holder.lastName.setText(user.getLastName());
            holder.age.setText(user.getAge());


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public ArrayList<Users> getUserList(){return this.list;}

        public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView firstName, lastName, age;
            OnClick onclick;
            public MyViewHolder(@NonNull View itemView, OnClick onclick) {
                super(itemView);

                firstName = itemView.findViewById(R.id.tvfirstName);
//                lastName = itemView.findViewById(R.id.tvlastName);
                age = itemView.findViewById(R.id.tvage);
                this.onclick = onclick;
                itemView.setOnClickListener(this);


            }
            @Override
            public void onClick(View view) {
                onclick.OnClick(getAdapterPosition());
            }
        }

        public interface OnClick{
            void OnClick(int position);
        }

    }