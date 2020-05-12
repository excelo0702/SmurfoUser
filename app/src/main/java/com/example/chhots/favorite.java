package com.example.chhots;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.chhots.R;
import com.example.chhots.bottom_navigation_fragments.Explore.VideoModel;
import com.example.chhots.category_view.routine.VideoAdapter;
import com.example.chhots.category_view.routine.routine;
import com.example.chhots.category_view.routine.routine_view;
import com.google.firebase.auth.FirebaseAuth;
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
public class favorite extends Fragment {
    public favorite() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    VideoAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private List<VideoModel> videolist;
    private static final String TAG = "Routine1235";
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        videolist = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_favorite_view);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        auth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("");
        if(mDatabaseRef.child("favorite").child(auth.getCurrentUser().getUid())!=null) {
            mDatabaseRef.child("favorite").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final List<String> VideoIds = new ArrayList<>();

                    Log.d(TAG, dataSnapshot.getValue().toString());
                    VideoModel model = dataSnapshot.getValue(VideoModel.class);
                    videolist.add((model));


                    mAdapter = new VideoAdapter(videolist, getContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }




        return view;
    }


}
