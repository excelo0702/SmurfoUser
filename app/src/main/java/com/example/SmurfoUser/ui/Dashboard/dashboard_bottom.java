package com.example.SmurfoUser.ui.Dashboard;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
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
import com.example.SmurfoUser.UserClass;
import com.example.SmurfoUser.UserInfoModel;
import com.example.SmurfoUser.bottom_navigation_fragments.Explore.VideoModel;
import com.example.SmurfoUser.ui.Dashboard.HistoryPackage.history;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private ImageView userImage,userBadge;
    TextView userName,userPoints,userLeaderbardName,userLeaderboardPoints;
    String cat="p";
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    RelativeLayout relativeLayout;
    CircularProgressBar circularProgressBar,circularProgressBar2,circularProgressBarcourse,circularProgressBarroutine,circularProgressBarinidvidual;

    PopupWindow mPopupWindow;
    String category = "Weekly";

    int points=0;
    FirebaseUser user;

    RecyclerView recyclerView;
    LeaderboardAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    List<LeaderboardModel> list,list2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard_bottom, container, false);

        init(view);


        user = FirebaseAuth.getInstance().getCurrentUser();

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

        userLeaderbardName = view.findViewById(R.id.userName_leaderboard_user);
        userLeaderboardPoints = view.findViewById(R.id.points_leaderboard_user);


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
                showLeaderboardUtil();
            }
        });

        if(!cat.equals("MainActivity"))
        {
            history.setEnabled(false);
            history.setVisibility(GONE);
            leaderboard.setVisibility(GONE);
            leaderboard.setEnabled(false);
        }

        fetchUserPoints();
        checkSubscription();
        return view;
    }


    private void fetchUserPoints() {
                        databaseReference.child("UsersPoint").child("OverAll").child(user.getUid()).addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        PointModel model = dataSnapshot.getValue(PointModel.class);
                                        Log.d("pop pop pop",dataSnapshot.getValue()+"");
                                        if(model!=null){
                                            points = model.getPoints();
                                            userPoints.setText(String.valueOf(points));
                                            if(points<200)
                                            {
                                                circularProgressBar2.setProgress((float)100.0*points/200);
                                                userBadge.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_star1));
                                            }
                                            else if(points<300 && points>200)
                                            {
                                                circularProgressBar2.setProgress((float)100.0*(points-200)/100);
                                                userBadge.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_star1));

                                            }
                                            else{
                                                circularProgressBar2.setProgress((float)100.0*(points-300)/(points+100));
                                                userBadge.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_star1));
                                            }

                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                                }
                        );

    }

    private void init(View view)
    {
        circularProgressBar = (CircularProgressBar)view.findViewById(R.id.progress_bar_chart_full);
        circularProgressBar.setColor(ContextCompat.getColor(getContext(), R.color.Smurfogreen));
        circularProgressBar.setBackgroundColor(Color.GRAY);

        circularProgressBarcourse = (CircularProgressBar)view.findViewById(R.id.progress_bar_chart_course);
        circularProgressBarcourse.setColor(ContextCompat.getColor(getContext(), R.color.Smurfogreen));
        circularProgressBarcourse.setBackgroundColor(Color.GRAY);

        circularProgressBarroutine = (CircularProgressBar)view.findViewById(R.id.progress_bar_chart_routine);
        circularProgressBarroutine.setColor(ContextCompat.getColor(getContext(), R.color.Smurfogreen));
        circularProgressBarroutine.setBackgroundColor(Color.GRAY);

        circularProgressBarinidvidual = (CircularProgressBar)view.findViewById(R.id.progress_bar_chart_individual);
        circularProgressBarinidvidual.setColor(ContextCompat.getColor(getContext(), R.color.Smurfogreen));
        circularProgressBarinidvidual.setBackgroundColor(Color.GRAY);

        circularProgressBar2 = (CircularProgressBar)view.findViewById(R.id.progress_bar_chart2);
        circularProgressBar2.setColor(ContextCompat.getColor(getContext(), R.color.Smurfogreen));
        circularProgressBar2.setBackgroundColor(Color.GRAY);

        userPoints = view.findViewById(R.id.user_dashboard_points);
        userBadge = view.findViewById(R.id.user_dashboard_badge);

    }

    @SuppressLint("ResourceAsColor")
    private void showLeaderboardUtil() {
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
        final TextView weekly = customView.findViewById(R.id.weekly);
        final TextView overAll = customView.findViewById(R.id.Overall);

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

        list = new ArrayList<>();
        userLeaderbardName = customView.findViewById(R.id.userName_leaderboard_user);
        userLeaderboardPoints = customView.findViewById(R.id.points_leaderboard_user);
        recyclerView = customView.findViewById(R.id.contest_leaderboards);
        mLayoutManager = new LinearLayoutManager(getContext());
        final List<LeaderboardModel> list = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        mAdapter = new LeaderboardAdapter(list,getContext());
        recyclerView.setHasFixedSize(true);

        category="weekly";
        showLeaderboard(category);
        showLeaderboardForUser(category);

        SpannableString content = new SpannableString("Weekly");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        weekly.setText(content);
        weekly.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.Smurfogreen));
        overAll.setText("OverAll");
        weekly.setTextSize(24);
        overAll.setTextSize(22);
        weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weekly.setTextSize(18);
                category="weekly";
                weekly.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.Smurfogreen));
                overAll.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.white));
                SpannableString content = new SpannableString("Weekly");
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                weekly.setText(content);
                overAll.setText("OverAll");
                weekly.setTextSize(22);
                overAll.setTextSize(20);
                showLeaderboard(category);
                showLeaderboardForUser(category);
            }
        });

        overAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overAll.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.Smurfogreen));
                weekly.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.white));
                SpannableString content = new SpannableString("OverAll");
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                weekly.setText("Weekly");
                overAll.setText(content);
                weekly.setTextSize(20);
                overAll.setTextSize(22);
                category="OverAll";
                showLeaderboardForUser(category);
                showLeaderboard(category);
            }
        });
    }

    private void showLeaderboard(String category) {
        Log.d("uuuuuu","  "+userName.getText().toString());
        list.clear();
        Query query = databaseReference.child("UsersPoint").child(category).orderByChild("points");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i=6;
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    if(list.size()>5)
                    {
                        break;
                    }
                    PointModel mode = ds.getValue(PointModel.class);
                    //TODO: change UserName
                    LeaderboardModel model = new LeaderboardModel(i+"."+mode.getName(),String.valueOf(mode.getPoints()),mode.getId());
                    list.add(0,model);
                    i--;
                }
                mAdapter.setData(list);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private void showLeaderboardForUser(String category) {

        Query query = databaseReference.child("UsersPoint").child(category).orderByChild("points");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("uuuuuu","p o  "+dataSnapshot.getValue());

                int pos=0;
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    pos++;
                    PointModel mode = ds.getValue(PointModel.class);
                    if(mode.getId().equals(user.getUid()))
                    {
                        userLeaderbardName.setText(String.valueOf(pos)+". "+userName.getText().toString());
                        userLeaderboardPoints.setText(String.valueOf(mode.getPoints()));
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public int dataDifference(String date1,String date2)
    {
        int k=0;

        String sy1 = date1.substring(0,4);
        String sy2 = date2.substring(0,4);

        int y = 365*(Integer.parseInt(sy1)-Integer.parseInt(sy2));


        String sm1 = date1.substring(5,7);
        String sm2 = date2.substring(5,7);
        int m = 30*(Integer.parseInt(sm1)-Integer.parseInt(sm2));

        String sd1 = date1.substring(8,10);
        String sd2 =  date2.substring(8,10);
        int d = Integer.parseInt(sd1)-Integer.parseInt(sd2);
        k = y+m+d+1;
        return k;
    }

    public void checkSubscription()
    {

        //kon sa subscription h uske pas


        final int[] flag = new int[1];

        final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


        //      mDatabaseReference.child("USER_PURCHASED_ROUTINES").child(userId).child("Individual").child()

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mDatabaseReference.child("USER_PURCHASED").child(user.getUid()).child("Course")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserClass model = dataSnapshot.getValue(UserClass.class);
                        //find category
                        if (model != null) {
                            int k = dataDifference(date, model.getDate());
                            if (model.getCategory().equals("1month")) {
                                if(k>30)
                                {
                                    circularProgressBarcourse.setProgressWithAnimation(100,20);
                                }
                                else
                                {
                                    circularProgressBarcourse.setProgressWithAnimation(k*100/30,20);
                                }
                            } else if (model.getCategory().equals("6month")) {
                                if(k>180)
                                {
                                    circularProgressBarcourse.setProgressWithAnimation(100,20);
                                }
                                else
                                {
                                    circularProgressBarcourse.setProgressWithAnimation(k*100/180,20);
                                }
                            } else if (model.getCategory().equals("1year")) {
                                if(k>30)
                                {
                                    circularProgressBarcourse.setProgressWithAnimation(100,40);
                                }
                                else
                                {
                                    circularProgressBarcourse.setProgressWithAnimation(k*100/365,40);
                                }
                            }
                        }
                        else
                        {
                            circularProgressBarcourse.setProgressWithAnimation(0,10);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });

        mDatabaseReference.child("USER_PURCHASED").child(user.getUid()).child("Routine")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserClass model = dataSnapshot.getValue(UserClass.class);
                        //find category
                        if (model != null) {
                            int k = dataDifference(date, model.getDate());
                            if (model.getCategory().equals("1month")) {
                                if(k>30)
                                {
                                    circularProgressBarroutine.setProgressWithAnimation(100,20);
                                }
                                else
                                {
                                    circularProgressBarroutine.setProgressWithAnimation(k*100/30,20);
                                }
                            } else if (model.getCategory().equals("6month")) {
                                if(k>180)
                                {
                                    circularProgressBarroutine.setProgressWithAnimation(100,20);
                                }
                                else
                                {
                                    circularProgressBarroutine.setProgressWithAnimation(k*100/180,20);
                                }
                            } else if (model.getCategory().equals("1year")) {
                                if(k>365)
                                {
                                    circularProgressBarroutine.setProgressWithAnimation(100,40);
                                }
                                else
                                {
                                    circularProgressBarroutine.setProgressWithAnimation(k*100/365,40);
                                }
                            }
                        }
                        else
                        {
                            circularProgressBarroutine.setProgressWithAnimation(0,10);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });

        mDatabaseReference.child("USER_PURCHASED").child(user.getUid()).child("Full")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserClass model = dataSnapshot.getValue(UserClass.class);
                        //find category
                        if (model != null) {
                            int k = dataDifference(date, model.getDate());
                            if (model.getCategory().equals("1month")) {
                                if(k>30)
                                {
                                    circularProgressBar.setProgressWithAnimation(100,20);
                                }
                                else
                                {
                                    circularProgressBar.setProgressWithAnimation(k*100/30,20);
                                }
                            } else if (model.getCategory().equals("6month")) {
                                if(k>180)
                                {
                                    circularProgressBar.setProgressWithAnimation(100,20);
                                }
                                else
                                {
                                    circularProgressBar.setProgressWithAnimation(k*100/180,20);
                                }
                            } else if (model.getCategory().equals("1year")) {
                                if(k>365)
                                {
                                    circularProgressBar.setProgressWithAnimation(100,40);
                                }
                                else
                                {
                                    circularProgressBar.setProgressWithAnimation(k*100/365,40);
                                }
                            }
                        }
                        else
                        {
                            circularProgressBar.setProgressWithAnimation(0,10);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });

        mDatabaseReference.child("USER_PURCHASED").child(user.getUid()).child("Individual")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserClass model = dataSnapshot.getValue(UserClass.class);
                        //find category
                        if (model != null) {
                            int k = dataDifference(date, model.getDate());
                            if (model.getCategory().equals("3days")) {
                                if(k>3)
                                {
                                    circularProgressBarinidvidual.setProgressWithAnimation(100,20);
                                }
                                else
                                {
                                    circularProgressBarinidvidual.setProgressWithAnimation(k*100/3,20);
                                }
                            } else if (model.getCategory().equals("10days")) {
                                if(k>10)
                                {
                                    circularProgressBarinidvidual.setProgressWithAnimation(100,20);
                                }
                                else
                                {
                                    circularProgressBarinidvidual.setProgressWithAnimation(k*100/10,20);
                                }
                            }
                        }
                        else
                        {
                            circularProgressBarinidvidual.setProgressWithAnimation(0,10);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });





    }

}
