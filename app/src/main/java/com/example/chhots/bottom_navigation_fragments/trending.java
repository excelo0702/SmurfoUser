package com.example.chhots.bottom_navigation_fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.chhots.R;
import com.example.chhots.bottom_navigation_fragments.Explore.VideoModel;
import com.example.chhots.category_view.routine.VideoAdapter;
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
public class trending extends Fragment {


    public trending() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    VideoAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private List<VideoModel> videolist;
    private static final String TAG = "Trending1235";
    private FirebaseAuth auth;
    TextView filter,sort;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_trending, container, false);
        videolist = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_trending_view);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        auth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("");
        filter = view.findViewById(R.id.filter_trending);
        sort = view.findViewById(R.id.sort_trending);


        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(),view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.sort_menu,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId())
                        {
                            case R.id.latest:
                                showTrending();
                                break;
                            case R.id.old:
                                showTrendingOld();
                                break;
                            default:
                                showTrending();
                                break;

                        }
                        return true;
                    }
                });
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(),view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.filter_menu,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId())
                        {
                            case R.id.street:
                                showTrendingStreet();
                                break;

                            case R.id.classical:
                                showTrendingClassical();
                                break;

                            case R.id.other:
                                showTrendingOthers();
                                break;

                            default:
                                showTrending();
                                break;
                        }
                        return true;
                    }
                });

            }
        });




        return view;
    }



    private void showTrending() {
        videolist.clear();

        mDatabaseRef.child("videos").limitToFirst(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d(TAG, dataSnapshot.getValue().toString());
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Log.d(TAG, ds.getValue().toString());
                    VideoModel model = ds.getValue(VideoModel.class);
                    videolist.add(model);

                }
                mAdapter = new VideoAdapter(videolist, getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showTrendingOld() {
        videolist.clear();

        mDatabaseRef.child("videos").limitToFirst(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d(TAG, dataSnapshot.getValue().toString());
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Log.d(TAG, ds.getValue().toString());
                    VideoModel model = ds.getValue(VideoModel.class);
                    videolist.add(model);

                }
                mAdapter = new VideoAdapter(videolist, getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showTrendinglowPrice() {
        videolist.clear();
        mDatabaseRef.child("videos").limitToFirst(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d(TAG, dataSnapshot.getValue().toString());
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Log.d(TAG, ds.getValue().toString());
                    VideoModel model = ds.getValue(VideoModel.class);
                    videolist.add(model);

                }
                mAdapter = new VideoAdapter(videolist, getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showTrendingHighPrice() {
        videolist.clear();

        mDatabaseRef.child("videos").limitToFirst(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d(TAG, dataSnapshot.getValue().toString());
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Log.d(TAG, ds.getValue().toString());
                    VideoModel model = ds.getValue(VideoModel.class);
                    videolist.add(model);

                }
                mAdapter = new VideoAdapter(videolist, getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showTrendingStreet() {
        videolist.clear();

        mDatabaseRef.child("videos").limitToFirst(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d(TAG, dataSnapshot.getValue().toString());
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Log.d(TAG, ds.getValue().toString());
                    VideoModel model = ds.getValue(VideoModel.class);
                    videolist.add(model);

                }
                mAdapter = new VideoAdapter(videolist, getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showTrendingClassical() {
        videolist.clear();

        mDatabaseRef.child("videos").limitToFirst(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d(TAG, dataSnapshot.getValue().toString());
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Log.d(TAG, ds.getValue().toString());
                    VideoModel model = ds.getValue(VideoModel.class);
                    videolist.add(model);

                }
                mAdapter = new VideoAdapter(videolist, getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showTrendingOthers() {
        videolist.clear();

        mDatabaseRef.child("videos").limitToFirst(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d(TAG, dataSnapshot.getValue().toString());
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Log.d(TAG, ds.getValue().toString());
                    VideoModel model = ds.getValue(VideoModel.class);
                    videolist.add(model);

                }
                mAdapter = new VideoAdapter(videolist, getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
