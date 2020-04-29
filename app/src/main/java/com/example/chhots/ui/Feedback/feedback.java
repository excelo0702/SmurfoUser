package com.example.chhots.ui.Feedback;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.chhots.R;

public class feedback extends Fragment {

    public feedback() {
    }

    RatingBar r1,r2,r3,r4;
    Button submit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_feedback, container, false);

        r1 = (RatingBar)root.findViewById(R.id.r1);
        r2 = (RatingBar)root.findViewById(R.id.r2);
        r3 = (RatingBar)root.findViewById(R.id.r3);
        r4 = (RatingBar)root.findViewById(R.id.r4);
        submit = (Button)root.findViewById(R.id.submit_feedback);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),String.valueOf(r1.getRating()),Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}