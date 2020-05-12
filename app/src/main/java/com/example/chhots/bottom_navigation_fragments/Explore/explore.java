package com.example.chhots.bottom_navigation_fragments.Explore;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chhots.R;
import com.example.chhots.category_view.routine.VideoAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class explore extends Fragment {


    public explore() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    VideoAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private List<VideoModel> videolist;
    private static final String TAG = "Explore";
    private TextView NormalVideos,ContestVideos,AddVideos;

    FirebaseUser user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_explore, container, false);

        videolist = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_explore_view);
        NormalVideos = view.findViewById(R.id.normal_videos);
        ContestVideos = view.findViewById(R.id.contest_videos);
        AddVideos = view.findViewById(R.id.add_normal_videos);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        user = FirebaseAuth.getInstance().getCurrentUser();

        showNormalVideos();

        NormalVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNormalVideos();
            }
        });

        ContestVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContestVideos();
            }
        });

        AddVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVideos();
            }
        });


        return view;
    }

    private void addVideos() {
        if(user==null)
        {
            Toast.makeText(getContext(),"You have to Sign in First",Toast.LENGTH_SHORT).show();
        }
        else {
            Fragment fragment = new upload_video();
            Bundle bundle = new Bundle();
            bundle.putString("subCategory", "NormalVideos");
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.drawer_layout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void showContestVideos() {
        videolist.clear();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("VIDEOS");
        mDatabaseRef.limitToLast(20).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d(TAG,dataSnapshot.getValue().toString()+"");
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {

                    VideoModel model = ds.getValue(VideoModel.class);
                    if(model.getSub_category()!=null && model.getSub_category().equals("CONTEST"))
                    {
                        videolist.add(model);
                        Log.d(TAG,dataSnapshot.getValue().toString()+"");

                    }

                }
                mAdapter = new VideoAdapter(videolist,getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showNormalVideos() {
        videolist.clear();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("VIDEOS");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                Log.d(TAG,dataSnapshot.getValue().toString()+"");
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Log.d(TAG,ds.getValue()+"");
                    VideoModel model = ds.getValue(VideoModel.class);
                    if(model.getSub_category()!=null && model.getSub_category().equals("NORMAL"))
                        videolist.add(model);
                }
                Collections.reverse(videolist);
                mAdapter = new VideoAdapter(videolist,getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
