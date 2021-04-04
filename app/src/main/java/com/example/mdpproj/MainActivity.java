package com.example.mdpproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mdpproj.db.AppDatabase;
import com.example.mdpproj.db.User;
import com.example.mdpproj.db.UserDao;

public class MainActivity extends AppCompatActivity {
    TextView usersTextView;
    EditText firstNameEditText;
    EditText lastNameEditText;
    AppDatabase database;
    UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        getSupportActionBar().hide();
        setContentView ( R.layout.activity_main );

        usersTextView = findViewById(R.id.register);
        firstNameEditText = findViewById(R.id.username);
        lastNameEditText = findViewById(R.id.password);

        database = AppDatabase.getDatabase(this);
        userDao = database.userDao();
    }
    public void saveUser(View view) {
        String firstName = firstNameEditText.getText ( ).toString ( );
        String lastName = lastNameEditText.getText ( ).toString ( );

        User u = new User ( );
        u.firstName = firstName;
        u.lastName = lastName;
        userDao.insert ( u );
    }
    public void registerUser(View view) {
        Intent intent = new Intent(view.getContext(), RegisterPage.class);
        startActivity(intent);
    }
}