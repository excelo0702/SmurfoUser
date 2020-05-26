package com.example.SmurfoUser.ui.Dashboard.ApproveVideo;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatPeopleList extends Fragment {


    public ChatPeopleList() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private ChatPeopleAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<ChatPeopleModel> list;

    private DatabaseReference mDatabaseReference;
    private static final String TAG = "ChatPeopleList";
    private FirebaseAuth auth;
    private FirebaseUser user;

    String routineId,instructorId;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chat_people_list, container, false);
        list = new ArrayList<>();
        recyclerView = view.findViewById(R.id.chat_pepole_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        Bundle bundle = getArguments();
        routineId = bundle.getString("routineId");
        instructorId = bundle.getString("instructorId");
        mAdapter = new ChatPeopleAdapter(list,getContext(),routineId);
        Log.d(TAG,routineId+"  p "+instructorId);

        showPeople();


        return view;
    }
    private void showPeople()
    {


        mDatabaseReference.child("CHAT_LIST").child(user.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren())
                        {
                            Log.d(TAG,ds.getValue()+" [ ");
                            ChatPeopleModel model = ds.getValue(ChatPeopleModel.class);
                            list.add(0,model);
                        }
                        mAdapter.setData(list);
                        mAdapter.notifyDataSetChanged();
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

}
