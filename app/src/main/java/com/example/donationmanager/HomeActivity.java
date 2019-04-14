package com.example.donationmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //activity elements
    private TextView textViewUserEmail;
    private Button buttonLogout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //initialise firebase object

        firebaseAuth = FirebaseAuth.getInstance();

        //

        if(firebaseAuth.getCurrentUser() == null) {
            //close activity
            finish();
            //go back to login
            startActivity(new Intent(this, LoginActivity.class));
        }

        //get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initialise view objects

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        //display account email
        textViewUserEmail.setText("Welcome"+user.getEmail());

        //add click listener on logout button
        buttonLogout.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {

        //if user logs out
        if (view == buttonLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

    }
}
