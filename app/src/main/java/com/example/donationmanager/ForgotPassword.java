package com.example.donationmanager;

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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private TextView title_tv, email_tv, cancel_tv;
    private Button reset_password_btn;
    private EditText email_txt;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        title_tv = (TextView) findViewById(R.id.title_tv);
        email_tv = (TextView) findViewById(R.id.email_tv);
        cancel_tv = (TextView) findViewById(R.id.cancel_tv);
        reset_password_btn = (Button) findViewById(R.id.reset_password_btn);
        email_txt = (EditText) findViewById(R.id.email_txt);
        reset_password_btn.setOnClickListener(this);
        cancel_tv.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

    }

    private void resetPassword() {

        CharSequence email = email_txt.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your Email", Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.sendPasswordResetEmail(email_txt.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPassword.this, "Check Email", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                } else {
                    Toast.makeText(ForgotPassword.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        if (view == reset_password_btn) {
            resetPassword();
        }

        if (view == cancel_tv) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}

