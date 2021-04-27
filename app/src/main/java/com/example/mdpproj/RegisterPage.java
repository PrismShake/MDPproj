package com.example.mdpproj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPage extends AppCompatActivity {

    //create objects
    EditText username, email, password, confirm_password;
    FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_page);

        //set objects
        username = findViewById(R.id.user);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pw);
        confirm_password = findViewById(R.id.password);
        Auth = FirebaseAuth.getInstance();

    }

    /*
      @param: view: base class for widgets
      @return: void
      desc: gets the users textfields and then registers them into application through Firebase
     */
    public void RegisterUser(View view){

        //gets the textfields
        String userName = username.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String Confirm_Password = confirm_password.getText().toString();

        //makes sure fields arent blank
        if(userName.isEmpty() || Email.isEmpty() || Password.isEmpty() || Confirm_Password.isEmpty()){
            Toast.makeText(this,"Please dont leave any fields blank",Toast.LENGTH_SHORT).show();
            return;
        }

        //makes sure password is strong
        if(Password.length() <= 6){
            Toast.makeText(this,"Password must be more than 6 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        //makes sure passwords matches
        if(!Password.equals(Confirm_Password)){
            Toast.makeText(this,"Passwords must match", Toast.LENGTH_SHORT).show();
            return;
        }

        /*C,reate a new createAccount method that takes in an email address and password, validates them,
          and then creates a new user with the createUserWithEmailAndPassword method.
         */
        Auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //if task is successfull user goes to questionaire that asks them a series of questions
                //which then displays answer in their profile page later on
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "User created", Toast.LENGTH_SHORT).show();
                    Log.i("Login", "User created");
                    Intent intent = new Intent(view.getContext(), Questionare.class);
                    intent.putExtra("Username",userName); //pushes that veriable into the next activity
                    startActivity(intent);

                    //if task fails, application displays errors like bad formatting of email or if the email is already in use
                }else{
                    Toast.makeText(getApplicationContext(),"Error! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    Log.i("Login","Error !" + task.getException().getMessage());
                }
            }
        });

        /*Intent intent = new Intent(getApplicationContext(), Questionare.class);
        intent.putExtra("Username",userName);
        startActivity(intent);*/
        Log.i("RegisterPage",userName);



    }

    /*
      @param: view: base class for widgets
      @return: void
      desc: if the user already has an account, this leads them back into the sign in page
   */
    public void GoToSignIn(View view) {
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        startActivity(intent);
    }
}