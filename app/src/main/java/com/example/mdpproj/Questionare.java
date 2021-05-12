package com.example.mdpproj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Questionare extends AppCompatActivity {

    private RecyclerView rv;
    List<QuestionObject> questionObjectList = new ArrayList<>();
    Questionare_Adapter questionare_adapter;
    FirebaseUser user;
    DatabaseReference mDatabase;
    String Username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionare);

        rv = findViewById(R.id.questions_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        questionObjectList.add(new QuestionObject(0,"Full Name "));
        questionObjectList.add(new QuestionObject(0, "Age "));
        questionObjectList.add(new QuestionObject(0,"Pronouns(Ex: she/her, he/him"));
        questionObjectList.add(new QuestionObject(0,"State(Ex: NY)"));
        questionObjectList.add(new QuestionObject(0,"City(Ex: NYC)"));
        questionObjectList.add(new QuestionObject(0,"Gym Name(Ex: Blink)"));

        int imgs[] = {R.drawable.endurance,R.drawable.strength,R.drawable.flexibility,R.drawable.balance};
        String texts[] = {"Endurance", "Strength", "Flexibility", "Balance"};

        questionObjectList.add(new QuestionObject(0,"What type of Workout?"));
        questionObjectList.add(new QuestionObject(0,"What is your motivation?"));

        questionare_adapter= new Questionare_Adapter(questionObjectList);

        rv.setAdapter(questionare_adapter);

        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");
        Log.i("Username", Username);

    }

    public void SubmitQuestionaire(View view) {

        String[] answers = questionare_adapter.getAnswersList();
        for(int i = 0; i < answers.length;i++)
            Log.i("Answers",answers[i]);

        UserObject u = new UserObject(Username, user.getUid(),answers[0],answers[1],answers[2],answers[3],answers[4],answers[5],answers[6],answers[7]);

        //Push stuff into database
        mDatabase.child("UserObject").child(u.getmUid()).setValue(u);
        //Push stuff into database
        //mDatabase.child("UserObject").push().setValue(u);

        Intent intent = new Intent(view.getContext(), DashBoardActivity.class);
        startActivity(intent);
    }
}