package com.example.donationmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignIn, buttonForgot;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;
    String initialSetup;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabase;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();



        if(firebaseAuth.getCurrentUser() != null) {
            firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getUid());
            //check if initial setup has been performed
            firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        initialSetup = dataSnapshot.child("initialSetup").getValue().toString();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            finish();
            Intent i = new Intent(getApplicationContext(),HomeActivity.class);
            i.putExtra("initialSetup", initialSetup);
            startActivity(i);
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
        }

        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
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

                    firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getUid());
                    //check if initial setup has been performed
                    firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                initialSetup = dataSnapshot.child("initialSetup").getValue().toString();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    if(initialSetup != null) {
                        finish();
                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                        i.putExtra("initialSetup", initialSetup);
                        startActivity(i);
                    }
                     else
                         startActivity(new Intent(getApplicationContext(),HomeActivity.class));


                } else {
                    //display fail message
                    Toast.makeText(LoginActivity.this,"Incorrect email address or password. Please try again.",Toast.LENGTH_LONG).show();
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
