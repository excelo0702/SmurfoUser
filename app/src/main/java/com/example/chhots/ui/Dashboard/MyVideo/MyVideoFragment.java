package com.example.chhots.ui.Dashboard.MyVideo;


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
public class MyVideoFragment extends Fragment {


    public MyVideoFragment() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    VideoAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private List<VideoModel> videolist;
    private static final String TAG = "VideoFragment";

    private FirebaseUser user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_video, container, false);

        videolist = new ArrayList<>();
        recyclerView = view.findViewById(R.id.my_video_dashboard);
        mAdapter = new VideoAdapter(videolist,getContext());
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        showVideos();



        return view;
    }

    private void showVideos() {
        videolist.clear();
        mDatabaseRef.child("USER_VIDEOS").child(user.getUid()).limitToLast(25)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d(TAG,dataSnapshot.getChildrenCount()+"");
                        for(DataSnapshot ds: dataSnapshot.getChildren())
                        {
                            Log.d(TAG,ds.getValue()+"");
                            VideoModel model = ds.getValue(VideoModel.class);
                            videolist.add(0, model);
                        }

                        mAdapter.setData(videolist);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setAdapter(mAdapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

}
