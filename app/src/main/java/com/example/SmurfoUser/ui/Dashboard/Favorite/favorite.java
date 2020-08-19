package com.example.SmurfoUser.ui.Dashboard.Favorite;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.SmurfoUser.R;
import com.example.SmurfoUser.bottom_navigation_fragments.Explore.VideoModel;
import com.example.SmurfoUser.bottom_navigation_fragments.Explore.VideoAdapter;
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
public class favorite extends Fragment {
    public favorite() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    VideoAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private List<VideoModel> videolist;

    private DatabaseReference mDatabaseRef;
    private static final String TAG = "Favorite";
    private FirebaseAuth auth;
    private FirebaseUser user;

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
        user = auth.getCurrentUser();
        showFavorites();

        return view;
    }

    private void showFavorites() {
        videolist.clear();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("FAVORITE").child(user.getUid());
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
                mAdapter = new VideoAdapter(videolist,getContext(),"Favorite");
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
