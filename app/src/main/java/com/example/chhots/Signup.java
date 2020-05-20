package com.example.chhots;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class Signup extends AppCompatActivity {

    private EditText email,password;
    Button signup;
    ProgressBar progressBar;
    TextInputLayout passwordTIL,emailTIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        signup = (Button)findViewById(R.id.signup);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        passwordTIL = findViewById(R.id.passwordTIL);
        emailTIL = findViewById(R.id.emailTIL);



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup.setEnabled(false);
                //disable button for 2 min

                String Semail = email.getText().toString().trim();
                String Spassword = password.getText().toString();

                if (TextUtils.isEmpty(Semail)) {
                    emailTIL.setFocusable(true);
                    emailTIL.setError("Enter Email Address");
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(Spassword)) {
                    passwordTIL.setFocusable(true);
                    passwordTIL.setError("Enter Password");

                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Spassword.length() < 7) {
                    emailTIL.setError("Password too short");
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 7 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(Signup.this,SignUpNextScreen.class);
                intent.putExtra("email",Semail);
                intent.putExtra("password",Spassword);
                startActivity(intent);
                progressBar.setVisibility(View.VISIBLE);
                signup.setEnabled(true);
                finish();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

}


