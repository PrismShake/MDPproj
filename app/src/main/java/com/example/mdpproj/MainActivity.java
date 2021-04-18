package com.example.mdpproj;


import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mdpproj.db.AppDatabase;
import com.example.mdpproj.db.User;
import com.example.mdpproj.db.UserDao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    //create objects
    EditText email, password;
    FirebaseAuth Auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        //set objects
        email = findViewById(R.id.user);
        password = findViewById(R.id.password);
        Auth = FirebaseAuth.getInstance();

    }

    /*
      @param: view: base class for widgets
      @return: void
      desc: gets the users textfields and then signs them into application through Firebase
     */
    public void SignIn(View view) {

        //gets textfields
        String Email = email.getText().toString();
        String Password = password.getText().toString();

        //Makes sure fields arent empty
        if(Email.isEmpty() || Password.isEmpty()){
            Toast.makeText(this, "Please dont leave any fields blank", Toast.LENGTH_SHORT).show();
            return;
        }

        //Makes sure password is strong
        if(Password.length() <= 6){
            Toast.makeText(this,"Password must be greater than 6",Toast.LENGTH_SHORT).show();
            return;
        }

        //authenticate the user

        Auth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //if task is successful, go on to main page and display "Logged in Successfully"
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Logged in Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view.getContext(), tabActivity.class);
                    startActivity(intent);
                    //Display error message whether it be if user entered wrong email & password or if user isnt in the database already
                }else{
                    Toast.makeText(getApplicationContext(),"Error! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    Log.i("Login","Error !" + task.getException().getMessage());
                }
            }
        });

    }

    /*
     @param: view: base class for widgets
    @return: void
    desc: if the user doesnt have an account, clicks on this and goes to register page
    */
    public void GoToRegister(View view) {
        Intent intent = new Intent(view.getContext(), RegisterPage.class);
        startActivity(intent);

    }
}