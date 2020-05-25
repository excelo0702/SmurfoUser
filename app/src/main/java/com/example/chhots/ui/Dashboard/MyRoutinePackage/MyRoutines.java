package com.example.chhots.ui.Dashboard.MyRoutinePackage;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.chhots.R;
import com.example.chhots.bottom_navigation_fragments.Explore.VideoModel;
import com.example.chhots.category_view.routine.RoutineAdapter;
import com.example.chhots.category_view.routine.RoutineThumbnailModel;
import com.example.chhots.category_view.routine.VideoAdapter;
import com.google.firebase.auth.FirebaseAuth;
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
public class MyRoutines extends Fragment {


    public MyRoutines() {
        // Required empty public constructor
    }


    RecyclerView recyclerView;
    RoutineAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private List<RoutineThumbnailModel> routinelist;
    private static final String TAG = "VideoFragment";

    private FirebaseUser user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_routines, container, false);


        routinelist = new ArrayList<>();
        recyclerView = view.findViewById(R.id.my_routine_dashboard);
        mAdapter = new RoutineAdapter(routinelist,getContext());
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        showVideos();

        return view;
    }

    private void showVideos() {
        mDatabaseRef.child("USER_PURCHASED_ROUTINES").child(user.getUid()).limitToLast(25)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren())
                        {
                            RoutineThumbnailModel model = ds.getValue(RoutineThumbnailModel.class);
                            routinelist.add(model);
                        }

                        mAdapter.setData(routinelist);
                        Log.d(TAG, routinelist.size() + "  mmm  ");
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setAdapter(mAdapter);



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

}
