package com.example.SmurfoUser.category_view.Courses;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SmurfoUser.R;
import com.example.SmurfoUser.category_view.routine.RoutineModel;
import com.example.SmurfoUser.category_view.routine.RoutineViewAdapter;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class course_view extends Fragment {


    public course_view() {
        // Required empty public constructor
    }

    RoutineViewAdapter adapter;
    ListView listview;
    ArrayList<RoutineModel> list;
    TextView about,courses;
    private DatabaseReference databaseReference;
    private String courseId;


    PlayerView playerView;
    SimpleExoPlayer player;

    private FirebaseUser user;
    String instructorId;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    ImageView fullScreenButton;
    boolean fullScreen = false;
    private Uri videouri;
    TextView lala_land;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =   inflater.inflate(R.layout.fragment_course_view, container, false);

        list = new ArrayList<>();

        about = (TextView)view.findViewById(R.id.about_course);
        courses = view.findViewById(R.id.courses_text);
        databaseReference = FirebaseDatabase.getInstance().getReference("");

        Bundle bundle = getArguments();
        courseId = bundle.getString("courseId");
        fetchCourse();

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new about_course();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.about_course_space,fragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    private void fetchCourse() {
        databaseReference.child("Courses").child(courseId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Log.d("11111kk",ds.getValue()+"");
                    UploadCourseModel model = ds.getValue(UploadCourseModel.class);
           //         list.add(model);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
