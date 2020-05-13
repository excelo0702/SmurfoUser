package com.example.chhots.ui.Feedback;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.chhots.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hsalf.smileyrating.SmileyRating;

import static com.paytm.pgsdk.easypay.actions.EasypayBrowserFragment.TAG;

public class feedback extends Fragment {

    public feedback() {
    }

    SmileyRating sr1,sr2,sr3,sr4;
    Button submit;
    EditText feedback;
    String description;

    int[] s = new int[4];
    DatabaseReference databaseReference;
    FirebaseUser user;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_feedback, container, false);

        sr1=root.findViewById(R.id.smile_rating1);
        sr2=root.findViewById(R.id.smile_rating2);
        sr3=root.findViewById(R.id.smile_rating3);
        sr4=root.findViewById(R.id.smile_rating4);
        submit = (Button)root.findViewById(R.id.submit_feedback);
        feedback = root.findViewById(R.id.feedback_text);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        sr1.setSmileySelectedListener(new SmileyRating.OnSmileySelectedListener() {
            @Override
            public void onSmileySelected(SmileyRating.Type type) {
                s[0] = type.getRating();
            }
        });
        sr1.setSmileySelectedListener(new SmileyRating.OnSmileySelectedListener() {
            @Override
            public void onSmileySelected(SmileyRating.Type type) {
                s[1] = type.getRating();
            }
        });
        sr1.setSmileySelectedListener(new SmileyRating.OnSmileySelectedListener() {
            @Override
            public void onSmileySelected(SmileyRating.Type type) {
                s[2] = type.getRating();
            }
        });
        sr1.setSmileySelectedListener(new SmileyRating.OnSmileySelectedListener() {
            @Override
            public void onSmileySelected(SmileyRating.Type type) {
                s[3] = type.getRating();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFeedback();
            }
        });

        return root;
    }

    private void saveFeedback()
    {
        description = feedback.getText().toString();
        if(description.equals(""))
        {
            description="User has nothing to say";

        }
        FeedbackModel model = new FeedbackModel(s[0],s[1],s[2],s[3],description);
        databaseReference.child("FEEDBACK").child(user.getUid()).setValue(model);

        if(databaseReference.child("FEEDBACK").child(user.getUid())==null)
        {
            Toast.makeText(getContext(),"Feedback Submitted ThankYou!!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getContext(),"Feedback Updated ThankYou!!",Toast.LENGTH_SHORT).show();
        }

    }

    public class FeedbackModel{
        public FeedbackModel() {
        }
        //name later
        int a,b,c,d;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        public int getC() {
            return c;
        }

        public void setC(int c) {
            this.c = c;
        }

        public int getD() {
            return d;
        }

        public void setD(int d) {
            this.d = d;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }

        public FeedbackModel(int a, int b, int c, int d, String feedback) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.feedback = feedback;
        }

        String feedback;

    }


}