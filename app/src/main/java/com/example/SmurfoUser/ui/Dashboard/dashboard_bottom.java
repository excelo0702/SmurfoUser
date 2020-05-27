package com.example.SmurfoUser.ui.Dashboard;


import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.SmurfoUser.LeaderboardAdapter;
import com.example.SmurfoUser.LeaderboardModel;
import com.example.SmurfoUser.R;
import com.example.SmurfoUser.UserInfoModel;
import com.example.SmurfoUser.bottom_navigation_fragments.Explore.VideoModel;
import com.example.SmurfoUser.ui.Dashboard.HistoryPackage.history;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class dashboard_bottom extends Fragment {


    public dashboard_bottom() {
        // Required empty public constructor
    }

    private Button history,leaderboard;
    BottomNavigationView bottomNavigationView;
    private ImageView userImage;
    TextView userName;
    String cat="p";
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    PopupWindow mPopupWindow;
    RelativeLayout relativeLayout;
    CircularProgressBar circularProgressBar,circularProgressBar2;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard_bottom, container, false);
        circularProgressBar = (CircularProgressBar)view.findViewById(R.id.progress_bar_chart);
        circularProgressBar.setColor(ContextCompat.getColor(getContext(), R.color.Smurfogreen));
        circularProgressBar.setBackgroundColor(Color.GRAY);
        int animationDuration = 2500; // 2500ms = 2,5s
        circularProgressBar.setProgressWithAnimation(75, animationDuration);

        circularProgressBar2 = (CircularProgressBar)view.findViewById(R.id.progress_bar_chart2);
        circularProgressBar2.setColor(ContextCompat.getColor(getContext(), R.color.Smurfogreen));
        circularProgressBar2.setBackgroundColor(Color.GRAY);
        circularProgressBar2.setProgressWithAnimation(45, animationDuration);


        Bundle bundle = this.getArguments();
        cat = bundle.getString("category");
        Log.d("main22222",cat);

        relativeLayout = view.findViewById(R.id.rr6);

        history = view.findViewById(R.id.history);
        leaderboard = view.findViewById(R.id.learderboard);
        View BottomnavBar = getActivity().findViewById(R.id.bottom_navigation);
        BottomnavBar.setVisibility(GONE);

        userImage = view.findViewById(R.id.user_dashboard_profile);
        userName = view.findViewById(R.id.user_dashboard_name);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("UserInfo").child(auth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserInfoModel model = dataSnapshot.getValue(UserInfoModel.class);
                        Picasso.get().load(Uri.parse(model.getUserImageurl())).into(userImage);
                        userName.setText(model.getUserName());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new history();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.dashboard_layout,fragment);
                fragmentTransaction.commit();
            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.contest_leaderboard,null);


                mPopupWindow = new PopupWindow(
                        customView,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

                if(Build.VERSION.SDK_INT>=21){
                    mPopupWindow.setElevation(5.0f);
                }
                ImageView close = customView.findViewById(R.id.close_leaderboard);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPopupWindow.dismiss();
                    }
                });
                mPopupWindow.setOutsideTouchable(true);
                mPopupWindow.setFocusable(true);
                mPopupWindow.showAtLocation(relativeLayout, Gravity.CENTER,0,0);
                mPopupWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                            mPopupWindow.dismiss();
                        }
                        return false;
                    }
                });

                final RecyclerView recyclerView = customView.findViewById(R.id.contest_leaderboard);
                final LeaderboardAdapter mAdapter;
                final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                final List<LeaderboardModel> list = new ArrayList<>();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                mAdapter = new LeaderboardAdapter(list,getContext());
                recyclerView.setHasFixedSize(true);

                Query query = databaseReference.child("UserInfo").orderByChild("points").limitToLast(40);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren())
                        {
                            VideoModel mode = ds.getValue(VideoModel.class);
                            LeaderboardModel model = new LeaderboardModel("UserName",mode.getLike(),mode.getUser());
                            list.add(0,model);
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
        });


        if(!cat.equals("MainActivity"))
        {
            history.setEnabled(false);
            history.setVisibility(GONE);
            leaderboard.setVisibility(GONE);
            leaderboard.setEnabled(false);
        }

        return view;
    }

}
