package com.example.SmurfoUser.User_Profile;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.SmurfoUser.MainActivity;
import com.example.SmurfoUser.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class user_account extends Fragment {


    EditText change_email,change_pswd;
    Button chng_email,chng_pswd,logout,delete_acc;
    ProgressBar progressBar1,progressBar2,progressBar3;
    FirebaseAuth auth;


    public user_account() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_user_account, container, false);

        change_email = (EditText)view.findViewById(R.id.change_email_text);
        change_pswd = (EditText)view.findViewById(R.id.change_password_text);
        chng_email = (Button)view.findViewById(R.id.change_email_button);
        chng_pswd = (Button)view.findViewById(R.id.change_password_button);
        logout = (Button)view.findViewById(R.id.logout);
        delete_acc = (Button)view.findViewById(R.id.delete_account_button);
        progressBar1 = (ProgressBar)view.findViewById(R.id.progressBar_email);
        progressBar2 = (ProgressBar)view.findViewById(R.id.progressBar_pswd);
        progressBar3 = (ProgressBar)view.findViewById(R.id.progressBar_logout);
        auth = FirebaseAuth.getInstance();

                chng_pswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(), "Failed to delete your account!", Toast.LENGTH_SHORT).show();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                 user.updatePassword(chng_pswd.getText().toString().trim())
            .addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Password is updated!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to update password!", Toast.LENGTH_SHORT).show();
                progressBar1.setVisibility(View.GONE);
            }
        }
    });

            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                auth.signOut();
                Toast.makeText(getContext(), "logged out", Toast.LENGTH_SHORT).show();


                if(auth.getCurrentUser()==null)
                {
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }

// this listener will be called when there is change in firebase user session
FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
            }
        };

            }
        });

        chng_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
       FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

user.updateEmail(change_email.getText().toString().trim())
            .addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Email address is updated.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Failed to update email!", Toast.LENGTH_LONG).show();
            }
        }
    });

            }
        });


        delete_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

       FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
if (user != null) {
    Toast.makeText(getContext(), "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
    user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), MainActivity.class));

                        } else {
                            Toast.makeText(getContext(), "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
            }
        });




        return view;
    }

}
