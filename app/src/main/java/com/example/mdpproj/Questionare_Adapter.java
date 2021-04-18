package com.example.mdpproj;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.example.mdpproj.Questions.LayoutOne;
import static com.example.mdpproj.Questions.LayoutTwo;

public class Questionare_Adapter extends RecyclerView.Adapter{
    private List<Questions> questionsList;

    public Questionare_Adapter(List<Questions> questionsList){
        this.questionsList = questionsList;
    }

    @Override
    public int getItemViewType(int position){
       switch(questionsList.get(position).getViewType()) {
           case 0:
               return LayoutOne;
           case 1:
               return LayoutTwo;
           default:
               return -1;

       }
    }

    class LayoutOneViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private LinearLayout linearLayout;

        public LayoutOneViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.questions);
            linearLayout = itemView.findViewById(R.id.lin_layout);
        }
        private void setView(String text){
            textView.setText(text);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch(viewType){
            case LayoutOne:
                View layoutOne = LayoutInflater.from(parent.getContext()).inflate(R.layout.questionaire_layout_one, parent, false);
                return new LayoutOneViewHolder(layoutOne);
            case LayoutTwo:
                View layoutTwo = LayoutInflater.from(parent.getContext()).inflate(R.layout.questionaire_layout_one, parent, false);
                return new LayoutOneViewHolder(layoutTwo);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch(questionsList.get(position).getViewType()){
            case LayoutOne:
                String text = questionsList.get(position).getText();
                ((LayoutOneViewHolder)holder).setView(text);
            case LayoutTwo:
                String textTwo = questionsList.get(position).getText();
                ((LayoutOneViewHolder)holder).setView(textTwo);
            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }
}
