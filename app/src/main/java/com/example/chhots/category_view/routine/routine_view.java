package com.example.chhots.category_view.routine;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chhots.ChatBox.ChatWithInstructor;
import com.example.chhots.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
public class routine_view extends Fragment {


    public routine_view() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private List<RoutineModel> list;
    private RoutineViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private String routineId;
    private FloatingActionButton chatBtn;

    private DatabaseReference mDatabaseReference;
    private FirebaseUser user;
    String instructorId;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_routine_view, container, false);

        list = new ArrayList<>();
        recyclerView = view.findViewById(R.id.routines_recycler_view_section);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new RoutineViewAdapter(list,getContext());
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();


        chatBtn = view.findViewById(R.id.chat_with_instructor_routine);

        Bundle bundle = getArguments();
        routineId = bundle.getString("routineId");
        fetchRoutine();

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChatWithInstructor.class);
                intent.putExtra("instructorId",instructorId);
                intent.putExtra("routineId",routineId);
                getContext().startActivity(intent);
            }
        });








        return view;
    }

    private void fetchRoutine() {
        mDatabaseReference.child("ROUTINEVIDEOS").child(routineId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren())
                        {
                            RoutineModel model = ds.getValue(RoutineModel.class);
                            list.add(model);
                            instructorId=model.getInstructorId();
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
