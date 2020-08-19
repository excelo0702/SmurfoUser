package com.example.SmurfoUser.category_view.Contest;


import android.animation.ArgbEvaluator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SmurfoUser.R;
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
public class contest extends Fragment {


    public contest() {
        // Required empty public constructor
    }

    ListView listview;
    ViewPager viewPager;
    ContestAdapter contestAdapter;
    List<HostModel> list;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    TextView participate;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private final String TAG = "Contest123";






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_contest, container, false);
        list = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("");
        viewPager = (ViewPager)view.findViewById(R.id.view_contest_Pager);

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
                    Toast.makeText(getContext(),"Connected",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "connected");
                } else {

                    Toast.makeText(getContext(),"Not Connected",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "not connected");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Listener was cancelled");
            }
        });


        showContests();

        return view;
    }

    private void showContests() {
        databaseReference.child("ContestThumbnail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Log.d(TAG,ds.getValue()+"\n");
                    HostModel model = ds.getValue(HostModel.class);
                    list.add(model);

                }
                contestAdapter = new ContestAdapter(list,getActivity());
                if(contestAdapter.getCount()>0){
                    Log.d(TAG,"not null");
                }
                else
                {
                    Log.d(TAG,"null");
                }
                viewPager.setAdapter(contestAdapter);
                if(viewPager==null)
                {
                    Log.d(TAG,"null viewPager");
                }
                else
                {
                    Log.d(TAG,"not null pager");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}
