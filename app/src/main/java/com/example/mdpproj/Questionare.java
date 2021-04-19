package com.example.mdpproj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class Questionare extends AppCompatActivity {

    private RecyclerView rv;
    List<Questions> questionsList = new ArrayList<>();
    Questionare_Adapter questionare_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionare);

        rv = findViewById(R.id.questions_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        questionsList.add(new Questions(0,"Full Name "));
        questionsList.add(new Questions(0, "Age "));
        questionsList.add(new Questions(0,"Pronouns(Ex: she/her, he/him"));
        questionsList.add(new Questions(0,"State(Ex: NY)"));
        questionsList.add(new Questions(0,"City(Ex: NYC)"));
        questionsList.add(new Questions(0,"Gym Name(Ex: Blink)"));

        int imgs[] = {R.drawable.endurance,R.drawable.strength,R.drawable.flexibility,R.drawable.balance};
        String texts[] = {"Endurance", "Strength", "Flexibility", "Balance"};

        questionsList.add(new Questions(0,"What type of Workout?"));
        questionsList.add(new Questions(0,"What is your motivation?"));

        questionare_adapter= new Questionare_Adapter(questionsList);

        rv.setAdapter(questionare_adapter);
    }

    public void SubmitQuestionaire(View view) {

       /* String[] answers = new String[questionsList.size()];
        for(int i = 0; i < answers.length;i++){
            RecyclerView.ViewHolder v = rv.findViewHolderForAdapterPosition(i);
            EditText currentAnswer = (EditText) v.itemView.findViewById(R.id.answers);
            String a = "";
            if(currentAnswer.getText() != null)
                a += currentAnswer.getText().toString();
            answers[i] = a;
        }

        for(int i = 0; i < answers.length; i++){
            Log.i("Answers", answers[i]);
        }*
        */

        String[] answers = questionare_adapter.getAnswersList();
        for(int i = 0; i < answers.length;i++)
            Log.i("Answers",answers[i]);

        Intent intent = new Intent(view.getContext(),tabActivity.class);
        startActivity(intent);
    }
}