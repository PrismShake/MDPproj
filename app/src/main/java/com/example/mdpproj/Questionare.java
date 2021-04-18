package com.example.mdpproj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Questionare extends AppCompatActivity {

    private RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionare);

        RecyclerView recyclerView = findViewById(R.id.questions_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<Questions> questionsList = new ArrayList<>();
        questionsList.add(new Questions(0,"Full Name "));
        questionsList.add(new Questions(0, "Date of Birth "));
        questionsList.add(new Questions(0,"What pronouns do you go by?"));
        questionsList.add(new Questions(0,"What state do you live in?"));
        questionsList.add(new Questions(0,"What city do you live in?"));
        questionsList.add(new Questions(0,"What gym do you go to?"));

        int imgs[] = {R.drawable.endurance,R.drawable.strength,R.drawable.flexibility,R.drawable.balance};
        String texts[] = {"Endurance", "Strength", "Flexibility", "Balance"};

        questionsList.add(new Questions(1,"What type of Workout?", imgs,texts));
        questionsList.add(new Questions(0,"What is your motivation?"));
        Questionare_Adapter questionare_adapter= new Questionare_Adapter(questionsList);


        recyclerView.setAdapter(questionare_adapter);
    }
}