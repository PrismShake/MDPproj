package com.example.mdpproj;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class buddiesPage extends AppCompatActivity {

    private List<Person> persons;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_buddies_page);

        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();
    }

    private void initializeData(){
        persons = new ArrayList<> ();
        persons.add(new Person("Emma Wilson", "23 years old", R.drawable.mdp_prof_backg));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.mdp_prof_backg));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.mdp_prof_backg));
    }

    private void initializeAdapter(){
        buddiesAdapter adapter = new buddiesAdapter (persons);
        rv.setAdapter(adapter);
    }
}