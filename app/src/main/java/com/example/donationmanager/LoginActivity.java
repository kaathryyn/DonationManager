package com.example.donationmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignIn, buttonForgot;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +                 //at least 1 digit
                    //"(?=.*[a-z])" +                 //at least 1 lower case
                    //"(?=.*[A-Z])" +                 //at least 1 upper case
                    //"(?=.*[!@#$%^&*+=?/<>~])" +     //at least 1 special char
                    //"(?=\\S+$)" +                   //no white spaces
                    ".{8,}" +                       //at least 8 char
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null) {
            //Home activity here

            finish();
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn =  (Button) findViewById(R.id.buttonSignin);
        buttonForgot = (Button) findViewById(R.id.buttonForgot);
        textViewSignup = (TextView) findViewById(R.id.textViewSignUp);
        progressDialog = new ProgressDialog(this);
        buttonSignIn.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);

        //attaching listeners
        buttonSignIn.setOnClickListener(this);
        buttonForgot.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);

    }

    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        //checks if an account already exists with that email address
        } else if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this,"An account already exists. Please enter a new email address",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        } else if(!PASSWORD_PATTERN.matcher(password).matches()){
            Toast.makeText(this,"Password must be at least 8 characters",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Logging in Please Wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if(task.isSuccessful()) {
                    //start app
                    finish();
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignIn) {
            userLogin();
        }

        if(view == buttonForgot) {
            finish();
            startActivity(new Intent(this, ForgotPassword.class));
        }

        if(view == textViewSignup) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

    }


}
