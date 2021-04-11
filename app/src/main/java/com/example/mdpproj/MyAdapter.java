package com.example.mdpproj;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter {
    List<String> data;

    MyAdapter(List<String> l){
        data = l;
    }

    static class  MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        View view = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.item_view,parent, false);
        return new MyViewHolder ( view );

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder , int position) {
        TextView tv = holder.itemView.findViewById ( R.id.item_view);
        tv.setText ( data.get ( position ) );
    }

    @Override
    public int getItemCount() {
        return data.size ();
    }

}

