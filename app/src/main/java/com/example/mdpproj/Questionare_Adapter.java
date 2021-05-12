package com.example.mdpproj;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.example.mdpproj.QuestionObject.LayoutOne;
import static com.example.mdpproj.QuestionObject.LayoutTwo;

/*
   Desc: Represents the Adapter for the RecyclerView in the Activity Questionaire
         Itegrates multiple types of Layout views representing diff kinds of questions
 */
public class Questionare_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    //questions
    private List<QuestionObject> questionObjectList;
    //user responses
    private String[] answersList;

    public Questionare_Adapter(){

    }
    //constructor for adapter
    public Questionare_Adapter(List<QuestionObject> questionObjectList){
        this.questionObjectList = questionObjectList;
        this.answersList = new String[questionObjectList.size()];
    }

    //returns the viewType of an item at a specific position in the RecyclerView
    @Override
    public int getItemViewType(int position){
       switch(questionObjectList.get(position).getViewType()) {
           case 0:
               return LayoutOne;
           case 1:
               return LayoutTwo;
           default:
               return -1;

       }
    }

    //returns the user responses
    //Used in the Questionaire class
    public String[] getAnswersList(){
        return this.answersList;
    }

    //Class for the first Layout
    class LayoutOneViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private LinearLayout linearLayout;
        private EditText editText;
        public LayoutOneViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.buddy_name );
            editText = itemView.findViewById(R.id.answers);
            linearLayout = itemView.findViewById(R.id.lin_layout);
            //need a text change listener which essentially helps to
            //obtain the content from the EditText in RecyclerView based off the position in the Adapter
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    answersList[getAdapterPosition()] = charSequence.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    //answersList[getAdapterPosition()] = editable.toString();
                }
            });

        }
        private void setView(String text, int pos){
            textView.setText(text);

        }
    }

    class LayoutTwoViewHolder extends RecyclerView.ViewHolder{

        private ImageView img1, img2, img3, img4;
        private TextView caption, text1,text2,text3,text4;
        public LayoutTwoViewHolder(@NonNull View itemView) {
            super(itemView);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
            img4 = itemView.findViewById(R.id.img4);
            text1 = itemView.findViewById(R.id.text_one);
            text2 = itemView.findViewById(R.id.text_two);
            text3 = itemView.findViewById(R.id.text_three);
            text4 = itemView.findViewById(R.id.text_four);
            caption = itemView.findViewById(R.id.main);
        }

        public void setViews(int[] images, String texts[], String c) {
            img1.setImageResource(images[0]);
            img2.setImageResource(images[1]);
            img3.setImageResource(images[2]);
            img4.setImageResource(images[3]);
            text1.setText(texts[0]);
            text2.setText(texts[1]);
            text3.setText(texts[2]);
            text4.setText(texts[3]);
            caption.setText(c);
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
                View layoutTwo = LayoutInflater.from(parent.getContext()).inflate(R.layout.questionaire_layout_two, parent, false);
                return new LayoutTwoViewHolder(layoutTwo);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        /*switch(questionObjectList.get(position).getViewType()){
            case LayoutOne:
                String text = questionObjectList.get(position).getText();
                ((LayoutOneViewHolder)holder).setView(text);
            case LayoutTwo:
                int[] images = questionObjectList.get(position).geticon();
                String[] texts = questionObjectList.get(position).getcaptions();
                String caption = questionObjectList.get(position).getText();
                Log.i("Layouttwo","Whats up");
                if(holder instanceof LayoutTwoViewHolder)
                    Log.i("Layouttwo","LayoutOne");
                   //((LayoutTwoViewHolder)holder).setViews(images,texts,caption);
            default:
                return;
        }*/

        if(questionObjectList.get(position).getViewType() == 0){
            String text = questionObjectList.get(position).getText();

            Log.i("Layouttwo","Whoop");
            //((LayoutOneViewHolder)holder).setView(text);
            LayoutOneViewHolder viewHolder = (LayoutOneViewHolder) holder;
            viewHolder.textView.setText(text);
            viewHolder.editText.setText(answersList[position]);
            //viewHolder.setView(text,position);

        }else if(questionObjectList.get(position).getViewType() == 1){
            int[] images = questionObjectList.get(position).geticon();
            String[] texts = questionObjectList.get(position).getcaptions();
            String caption = questionObjectList.get(position).getText();
            Log.i("Layouttwo","Whats up");
            LayoutTwoViewHolder viewHolderTwo = (LayoutTwoViewHolder) holder;
            viewHolderTwo.setViews(images,texts,caption);
        }
    }

    //returns the number of items in the recycler view
    @Override
    public int getItemCount() {
        return questionObjectList.size();
    }
}
