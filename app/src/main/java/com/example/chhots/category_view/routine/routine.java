package com.example.chhots.category_view.routine;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chhots.R;
import com.example.chhots.bottom_navigation_fragments.Explore.VideoModel;
import com.example.chhots.bottom_navigation_fragments.Explore.upload_video;
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

public class routine extends Fragment{

    public routine() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    VideoAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private TextView filter,sort,addRoutine;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private List<VideoModel> videolist;
    private static final String TAG = "Routine";

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_routine, container, false);

        videolist = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_routine_view);
        filter = view.findViewById(R.id.fliter_routine);
        sort = view.findViewById(R.id.sort_routine);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        addRoutine = view.findViewById(R.id.add_routine_video);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        showRoutine();



        addRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVideos();
            }
        });

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
                                showRoutine();
                                break;
                            case R.id.old:
                                showRoutineOld();
                                break;
                            case R.id.price_low:
                                showRoutinelowPrice();
                                break;
                            case R.id.price_high:
                                showRoutineHighPrice();
                                break;
                                default:
                                    showRoutine();
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
                                showRoutineStreet();
                                break;

                            case R.id.classical:
                                showRoutineClassical();
                                break;

                            case R.id.other:
                                showRoutineOthers();
                                break;

                            default:
                                showRoutine();
                                break;
                        }
                        return true;
                    }
                });

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
            bundle.putString("subCategory", "RoutineVideos");
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.drawer_layout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


    private void showRoutine() {
        videolist.clear();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("VIDEOS");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                Log.d(TAG,dataSnapshot.getValue().toString()+"");
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    VideoModel model = ds.getValue(VideoModel.class);
                    if(model.getSub_category()!=null && model.getSub_category().equals("Routine"))
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

    private void showRoutineOld() {
        videolist.clear();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("videos");
        mDatabaseRef.limitToLast(20).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                Log.d(TAG,dataSnapshot.getValue().toString()+"");
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    VideoModel model = ds.getValue(VideoModel.class);
                    videolist.add(model);
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

    private void showRoutinelowPrice() {
        videolist.clear();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("videos");
        mDatabaseRef.limitToFirst(20).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                Log.d(TAG,dataSnapshot.getValue().toString()+"");
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    VideoModel model = ds.getValue(VideoModel.class);
                    videolist.add(model);
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

    private void showRoutineHighPrice() {
        videolist.clear();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("videos");
        mDatabaseRef.limitToFirst(20).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                Log.d(TAG,dataSnapshot.getValue().toString()+"");
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    VideoModel model = ds.getValue(VideoModel.class);
                    videolist.add(model);
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

    private void showRoutineStreet() {
        videolist.clear();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("videos");
        mDatabaseRef.limitToFirst(20).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                Log.d(TAG,dataSnapshot.getValue().toString()+"");
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    VideoModel model = ds.getValue(VideoModel.class);
                    videolist.add(model);
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

    private void showRoutineClassical() {
        videolist.clear();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("videos");
        mDatabaseRef.limitToFirst(20).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                Log.d(TAG,dataSnapshot.getValue().toString()+"");
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    VideoModel model = ds.getValue(VideoModel.class);
                    videolist.add(model);
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

    private void showRoutineOthers() {
        videolist.clear();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("videos");
        mDatabaseRef.limitToFirst(20).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                Log.d(TAG,dataSnapshot.getValue().toString()+"");
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    VideoModel model = ds.getValue(VideoModel.class);
                    videolist.add(model);
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





}
