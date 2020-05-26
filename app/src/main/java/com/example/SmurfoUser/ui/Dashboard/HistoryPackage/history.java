package com.example.SmurfoUser.ui.Dashboard.HistoryPackage;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.SmurfoUser.R;
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
public class history extends Fragment {


    public history() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private HistoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<HistoryModel> list;


    private DatabaseReference mDatabaseRef;
    private static final String TAG = "Favorite";
    private FirebaseAuth auth;
    private FirebaseUser user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        list = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_history_view);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        showHistory();

        return view;
    }

    private void showHistory() {
        list.clear();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("HISTORY").child(user.getUid());
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    HistoryModel model = ds.getValue(HistoryModel.class);
                    list.add(model);
                }
                Collections.reverse(list);
                mAdapter = new HistoryAdapter(list,getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
