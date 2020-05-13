package com.example.chhots.bottom_navigation_fragments.Explore;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chhots.R;
import com.example.chhots.category_view.routine.VideoAdapter;
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
public class explore extends Fragment {


    public explore() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    VideoAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private List<VideoModel> videolist;
    private static final String TAG = "Explore";
    private TextView NormalVideos,ContestVideos,AddVideos;

    int currentItems,scrolloutItems,TotalItems;
    String mLastKey,category,tempkey;

    FirebaseUser user;


    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    int flag=0;
    boolean isScrolling=false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_explore, container, false);

        videolist = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_explore_view);
        NormalVideos = view.findViewById(R.id.normal_videos);
        ContestVideos = view.findViewById(R.id.contest_videos);
        AddVideos = view.findViewById(R.id.add_normal_videos);
        progressBar =view.findViewById(R.id.explore_progressbar);
        mAdapter = new VideoAdapter(videolist,getContext());
        category="";
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("VIDEOS");

        category="NORMAL";
        swipeRefreshLayout = view.findViewById(R.id.swipe_explore);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                if(flag==0)
                {
                    showVideos(category);
                }
                else
                {
                    showVideos(category);
                }
            //    showRoutine(category);
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        showVideos(category);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling=true;
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = mLayoutManager.getChildCount();
                TotalItems = mLayoutManager.getItemCount();
                scrolloutItems = mLayoutManager.findFirstVisibleItemPosition();


                if(isScrolling && currentItems+scrolloutItems==TotalItems)
                {
                    isScrolling=false;
                    progressBar.setVisibility(View.VISIBLE);
                    datafetch(category);


                }

            }
        });

        NormalVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=0;
                category="NORMAL";
                showVideos(category);
            }
        });

        ContestVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=1;
                category="Contest";
                showVideos(category);
            }
        });

        AddVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVideos();
            }
        });


        return view;
    }

    private void addVideos() {
        if(user==null)
        {
            Toast.makeText(getContext(),"You have to Sign in First",Toast.LENGTH_SHORT).show();
        }
        else {
            Fragment fragment = new upload_video();
            Bundle bundle = new Bundle();
            bundle.putString("subCategory", "NormalVideos");
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.drawer_layout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


    private void datafetch(final String category) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mLastKey == null) {
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                final int k = videolist.size();
                recyclerView.smoothScrollToPosition(videolist.size() - 1);
                mDatabaseRef.child("VIDEOS").orderByKey().endAt(mLastKey).limitToLast(10).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int p=0;
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                VideoModel model = ds.getValue(VideoModel.class);
                                Log.d(TAG, model.getVideoId() + " p p p ");

                                if (model.getSub_category() != null && model.getSub_category().equals(category)) {
                                    videolist.add(k - 1, model);
                                }
                                if (videolist.size() == 6 + k) {
                                    mLastKey = videolist.get(k).getVideoId();
                                    break;
                                }
                                if(p==0) {
                                    tempkey = model.getVideoId();
                                }
                                p++;
                            }
                            if (videolist.size() < 6 + k) {
                                mLastKey = tempkey;
                            }
                            mAdapter.setData(videolist);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setAdapter(mAdapter);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });


            }
        },2000);
    }




    private void showVideos(final String category) {
        videolist.clear();
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                int k=0;
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {

                    VideoModel model = ds.getValue(VideoModel.class);
                    if (model.getSub_category() != null && model.getSub_category().equals(category)) {
                        videolist.add(0, model);
                    }
                    if(videolist.size()>6)
                    {
                        break;
                    }
                    if(k==0) {
                        tempkey = model.getVideoId();
                    }
                    k++;
                }
                if(videolist.size()==0)
                {
                    mLastKey=tempkey;
                }
                else
                {
                    mLastKey = videolist.get(videolist.size() - 1).getVideoId();
                }
                //TODO:little change in queries of routine
                mAdapter.setData(videolist);
                Log.d(TAG, videolist.size() + "  mmm  ");
                Log.d(TAG, mLastKey + " ooo ");
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

}
