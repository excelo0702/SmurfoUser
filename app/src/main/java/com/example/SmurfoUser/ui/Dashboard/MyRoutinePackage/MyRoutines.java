package com.example.SmurfoUser.ui.Dashboard.MyRoutinePackage;


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

import com.example.SmurfoUser.R;
import com.example.SmurfoUser.UserInfoModel;
import com.example.SmurfoUser.category_view.routine.RoutineAdapter;
import com.example.SmurfoUser.category_view.routine.RoutineThumbnailModel;
import com.example.SmurfoUser.ui.Dashboard.ApproveVideo.ApproveVideoAdapter;
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
    MyRoutineAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    private DatabaseReference mDatabaseReference;
    private List<MyRoutineModel> list;

    private FirebaseUser user;
    private UserInfoModel User_model;
    private String TAG="ApproveVideo";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_routines, container, false);
        recyclerView = view.findViewById(R.id.my_routine_dashboard);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        list = new ArrayList<>();
        mAdapter = new MyRoutineAdapter(list,getContext());
        user = FirebaseAuth.getInstance().getCurrentUser();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        DatabaseReference presenceRef = FirebaseDatabase.getInstance().getReference("disconnectmessage");
        presenceRef.onDisconnect().setValue("I disconnected!");
        presenceRef.onDisconnect().removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, @NonNull DatabaseReference reference) {
                if (error != null) {
                    Log.d(TAG, "could not establish onDisconnect event:" + error.getMessage());
                }
            }
        });

        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    Log.d(TAG, "connected");
                } else {
                    Log.d(TAG, "not connected");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Listener was cancelled");
            }
        });
        showVideos();
        return view;
    }

    private void showVideos() {
        mDatabaseReference.child("MyRoutines").child(user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren())
                        {
                            RoutineThumbnailModel mode = ds.getValue(RoutineThumbnailModel.class);
                            Log.d("pop pop ",mode.getRoutineThumbnail()+" pop  ");
                            MyRoutineModel model = new MyRoutineModel(mode.getTitle(),mode.getRoutineId(),mode.getRoutineThumbnail(),mode.getInstructorId());
                            list.add(0,model);
                        }
                        mAdapter.setData(list);
                        Log.d(TAG, list.size() + "  mmm  ");
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

}
