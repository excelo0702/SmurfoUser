package com.example.SmurfoUser.ui.Dashboard.ApproveVideo;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.SmurfoUser.R;
import com.example.SmurfoUser.category_view.routine.RoutineThumbnailModel;
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
public class ApproveVideo extends Fragment {


    public ApproveVideo() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    ApproveVideoAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    private DatabaseReference mDatabaseReference;
    private List<RoutineThumbnailModel> list;

    private FirebaseUser user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_approve_video, container, false);
        recyclerView = view.findViewById(R.id.routine_videos);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        list = new ArrayList<>();
        mAdapter = new ApproveVideoAdapter(list,getContext());
        user = FirebaseAuth.getInstance().getCurrentUser();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        fetchRoutine();


        return view;
    }

    private void fetchRoutine() {
        mDatabaseReference.child(getString(R.string.RoutineThumbnail))
           .addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   for(DataSnapshot ds: dataSnapshot.getChildren())
                   {
                       RoutineThumbnailModel model = ds.getValue(RoutineThumbnailModel.class);
                       if(model.getInstructor_name().equals(user.getUid()))
                       {
                           list.add(0,model);
                       }
                   }
                   mAdapter.setData(list);
                   recyclerView.setLayoutManager(mLayoutManager);
                   recyclerView.setAdapter(mAdapter);
               }
               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });
    }

}
