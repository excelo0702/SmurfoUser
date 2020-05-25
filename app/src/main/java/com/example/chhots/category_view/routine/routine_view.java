package com.example.chhots.category_view.routine;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.chhots.ChatBox.ChatWithInstructor;
import com.example.chhots.R;
import com.example.chhots.ui.Dashboard.HistoryPackage.HistoryModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

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
    private ImageView chatBtn;
    RelativeLayout rr2,fl;
    String category="";
    int flag;
    RelativeLayout tt,rr5;

    String htitle,hvideoName,hthumbnail;



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
        rr2 = view.findViewById(R.id.rr3);
        rr5 = view.findViewById(R.id.rr5);
        fl = view.findViewById(R.id.video_space_routine);
        user = FirebaseAuth.getInstance().getCurrentUser();


        chatBtn = view.findViewById(R.id.chat_with_instructor_routine);

        Bundle bundle = getArguments();
        category = bundle.getString("category");
        if(category.equals("VideoView"))
        {
            Log.d("1111111","222222");
            routineId = bundle.getString("routineId");
            flag = bundle.getInt("Flag");
            if(flag==0) {

                rr5.setVisibility(GONE);
                fl.setVisibility(GONE);
                chatBtn.setVisibility(GONE);
                chatBtn.setEnabled(false);
                recyclerView.setVisibility(GONE);
                rr2.setVisibility(GONE);

                View BottomnavBar = getActivity().findViewById(R.id.bottom_navigation);
                BottomnavBar.setVisibility(GONE);

                Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
                toolbar.setVisibility(GONE);

                tt = getActivity().findViewById(R.id.tt);
                tt.setVisibility(GONE);

                View NavBar = getActivity().findViewById(R.id.nav_view);
                NavBar.setVisibility(GONE);

            }
            else {
                chatBtn.setVisibility(View.VISIBLE);
                chatBtn.setEnabled(true);
                rr2.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)fl.getLayoutParams();
                params.width = params.MATCH_PARENT;
                params.height = (int)( 0 * getContext().getResources().getDisplayMetrics().density);
                fl.setLayoutParams(params);
                fl.setVisibility(GONE);


                RelativeLayout.LayoutParams para = (RelativeLayout.LayoutParams)rr5.getLayoutParams();
                para.height = params.MATCH_PARENT;
                para.height = params.MATCH_PARENT;
                rr5.setLayoutParams(para);

                RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)rr2.getLayoutParams();
                param.width = param.MATCH_PARENT;
                param.height = param.MATCH_PARENT;
                rr2.setLayoutParams(param);

                recyclerView.setVisibility(View.VISIBLE);
                fetchRoutine();
            }
        }
        else {
            routineId = bundle.getString("routineId");
            fetchRoutine();

            chatBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), ChatWithInstructor.class);
                    intent.putExtra("instructorId", instructorId);
                    intent.putExtra("routineId", routineId);
                    intent.putExtra("category","User");
                    getContext().startActivity(intent);
                }
            });


        }
        return view;
    }

    private void pushHistory() {
        String time = System.currentTimeMillis()+"";
        HistoryModel model = new HistoryModel("Routine",hvideoName,hthumbnail,"date","Routine",routineId);
        mDatabaseReference.child("HISTORY").child(user.getUid()).child(time).setValue(model);
    }

    private void fetchRoutine() {
        mDatabaseReference.child("ROUTINEVIDEOS").child(routineId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren())
                        {
                            //TODO: Add thumbnail
                            RoutineModel model = ds.getValue(RoutineModel.class);
                            list.add(model);
                            instructorId=model.getInstructorId();
                            hvideoName = model.getTitle();
                            if(model.getThumbnail()!=null)
                                hthumbnail = model.getThumbnail();
                        }
                        mAdapter.setData(list);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        pushHistory();

    }

}
