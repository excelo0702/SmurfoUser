package com.example.chhots.category_view.routine;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chhots.R;
import com.example.chhots.bottom_navigation_fragments.Explore.VideoModel;
import com.example.chhots.bottom_navigation_fragments.Explore.upload_video;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class routine extends Fragment{

    public routine() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    VideoAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    private TextView filter,sort,addRoutine;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private List<VideoModel> videolist;
    private static final String TAG = "Routine";
    boolean isScrolling=false;
    int currentItems,scrolloutItems,TotalItems;
    String mLastKey,category,tempkey;

    FirebaseAuth auth;
    FirebaseUser user;

    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    int fi=0,so=0;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_routine, container, false);

        videolist = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_routine_view);
        filter = view.findViewById(R.id.fliter_routine);
        sort = view.findViewById(R.id.sort_routine);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        addRoutine = view.findViewById(R.id.add_routine_video);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mDatabaseRef =FirebaseDatabase.getInstance().getReference();
        progressBar =view.findViewById(R.id.routine_progressbar);
        mAdapter = new VideoAdapter(videolist,getContext());
        category="";
        swipeRefreshLayout = view.findViewById(R.id.swipe_routine);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                showRoutine(category);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        showRoutine(category);


        addRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVideos();
            }
        });

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
                    if(so==0){
                        datafetch(category);
                    }
                    else if(so==1)
                    {
                        datafetchOld(category);
                    }
                    else if(so==2)
                    {
                        datafetchlowPrice(category);
                    }
                    else if(so==3)
                    {
                        datafetchHighPrice(category);
                    }


                }
            }
        });


        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(),view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.sort_menu,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId())
                        {
                            case R.id.latest:
                                so=0;
                                showRoutine(category);
                                break;
                            case R.id.old:
                                so=1;
                                showRoutineOld(category);
                                break;
                            case R.id.price_low:
                                so=2;
                                showRoutinelowPrice(category);
                                break;
                            case R.id.price_high:
                                so=3;
                                showRoutineHighPrice(category);
                                break;
                                default:
                                    so=0;
                                    showRoutine(category);
                                    break;

                        }
                        return true;
                    }
                });
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(),view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.filter_menu,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId())
                        {
                            case R.id.street:

                                category = "Street";
                                showRoutine(category);
                                break;

                            case R.id.classical:
                                category = "Classical";
                                showRoutine(category);
                                break;

                            case R.id.other:
                                category = "Others";
                                showRoutine(category);
                                break;

                            default:
                                category="";
                                showRoutine(category);
                                break;
                        }
                        return true;
                    }
                });

            }
        });

        return view;
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
                if (category.equals(""))
                {
                    mDatabaseRef.child("VIDEOS").orderByKey().endAt(mLastKey).limitToLast(10).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                VideoModel model = ds.getValue(VideoModel.class);
                                Log.d(TAG, model.getVideoId() + " p p p ");

                                if (model.getSub_category() != null && model.getSub_category().equals("ROUTINE")) {
                                    videolist.add(k - 1, model);
                                }
                                if (videolist.size() == 6 + k) {
                                    mLastKey = videolist.get(k).getVideoId();
                                    break;
                                }
                            }
                            if (videolist.size() < 6 + k) {
                                mLastKey = null;
                            }
                            mAdapter.setData(videolist);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setAdapter(mAdapter);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });
                }
                else
                {
                    mDatabaseRef.child("VIDEOS").orderByKey().endAt(mLastKey).limitToLast(10).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                VideoModel model = ds.getValue(VideoModel.class);
                                Log.d(TAG, model.getVideoId() + " p p p ");

                                if (model.getSub_category() != null && model.getSub_category().equals("ROUTINE") && model.getCategory().equals(category)) {
                                    videolist.add(k - 1, model);
                                }
                                if (videolist.size() == 6 + k) {
                                    mLastKey = videolist.get(k).getVideoId();
                                    break;
                                }
                            }
                            if (videolist.size() < 6 + k) {
                                mLastKey = null;
                            }
                            mAdapter.setData(videolist);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setAdapter(mAdapter);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });
                }
            }
        },2000);
    }


    private void datafetchOld(final String category) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mLastKey == null) {
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                final int k = videolist.size();
                recyclerView.smoothScrollToPosition(videolist.size() - 1);
                if (category.equals(""))
                {
                    mDatabaseRef.child("VIDEOS").orderByKey().startAt(mLastKey).limitToFirst(10).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                VideoModel model = ds.getValue(VideoModel.class);
                                Log.d(TAG, model.getVideoId() + " p p p ");

                                if (model.getSub_category() != null && model.getSub_category().equals("ROUTINE")) {
                                    videolist.add(model);
                                }
                                if (videolist.size() == 6 + k) {
                                    mLastKey = videolist.get(k).getVideoId();
                                    break;
                                }
                            }
                            if (videolist.size() < 6 + k) {
                                mLastKey = null;
                            }
                            mAdapter.setData(videolist);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setAdapter(mAdapter);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });
                }
                else
                {
                    mDatabaseRef.child("VIDEOS").orderByKey().startAt(mLastKey).limitToFirst(10).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                VideoModel model = ds.getValue(VideoModel.class);
                                Log.d(TAG, model.getVideoId() + " p p p ");

                                if (model.getSub_category() != null && model.getSub_category().equals("ROUTINE") && model.getCategory().equals(category)) {
                                    videolist.add(model);
                                }
                                if (videolist.size() == 6 + k) {
                                    mLastKey = videolist.get(k).getVideoId();
                                    break;
                                }
                            }
                            if (videolist.size() < 6 + k) {
                                mLastKey = null;
                            }
                            mAdapter.setData(videolist);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setAdapter(mAdapter);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });
                }
            }
        },2000);
    }


    private void datafetchlowPrice(final String category) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mLastKey == null) {
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                final int k = videolist.size();
                recyclerView.smoothScrollToPosition(videolist.size() - 1);

                Query query = mDatabaseRef.child("VIDEOS").orderByChild("price").startAt(mLastKey).limitToFirst(10);
                if (category.equals(""))
                {
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                VideoModel model = ds.getValue(VideoModel.class);
                                Log.d(TAG, model.getVideoId() + " p p p ");

                                if (model.getSub_category() != null && model.getSub_category().equals("ROUTINE")) {
                                    videolist.add(model);
                                }
                                if (videolist.size() == 6 + k) {
                                    mLastKey = videolist.get(k).getVideoId();
                                    break;
                                }
                            }
                            if (videolist.size() < 6 + k) {
                                mLastKey = null;
                            }
                            mAdapter.setData(videolist);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setAdapter(mAdapter);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });
                }
                else
                {
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                VideoModel model = ds.getValue(VideoModel.class);
                                Log.d(TAG, model.getVideoId() + " p p p ");

                                if (model.getSub_category() != null && model.getSub_category().equals("ROUTINE") && model.getCategory().equals(category)) {
                                    videolist.add(model);
                                }
                                if (videolist.size() == 6 + k) {
                                    mLastKey = videolist.get(k).getVideoId();
                                    break;
                                }
                            }
                            if (videolist.size() < 6 + k) {
                                mLastKey = null;
                            }
                            mAdapter.setData(videolist);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setAdapter(mAdapter);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });
                }
            }
        },2000);
    }



    //TODO: high low filter issue
    private void datafetchHighPrice(final String category) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mLastKey == null) {
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                final int k = videolist.size();
                recyclerView.smoothScrollToPosition(videolist.size() - 1);

                Query query = mDatabaseRef.child("VIDEOS").orderByChild("price").limitToLast(10);
                if (category.equals(""))
                {
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                VideoModel model = ds.getValue(VideoModel.class);
                                Log.d(TAG, model.getVideoId() + " p p p ");

                                if (model.getSub_category() != null && model.getSub_category().equals("ROUTINE")) {
                                    videolist.add(0,model);
                                }
                                if (videolist.size() == 6 + k) {
                                    mLastKey = videolist.get(k).getVideoId();
                                    break;
                                }
                            }
                            if (videolist.size() < 6 + k) {
                                mLastKey = null;
                            }
                            mAdapter.setData(videolist);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setAdapter(mAdapter);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });
                }
                else
                {
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                VideoModel model = ds.getValue(VideoModel.class);
                                Log.d(TAG, model.getVideoId() + " p p p ");

                                if (model.getSub_category() != null && model.getSub_category().equals("ROUTINE") && model.getCategory().equals(category)) {
                                    videolist.add(0,model);
                                }
                                if (videolist.size() == 6 + k) {
                                    mLastKey = videolist.get(k).getVideoId();
                                    break;
                                }
                            }
                            if (videolist.size() < 6 + k) {
                                mLastKey = null;
                            }
                            mAdapter.setData(videolist);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setAdapter(mAdapter);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });
                }
            }
        },2000);
    }


    private void addVideos() {

        if(user==null)
        {
            Toast.makeText(getContext(),"You have to Sign in First",Toast.LENGTH_SHORT).show();
        }
        else {
            Fragment fragment = new upload_video();
            Bundle bundle = new Bundle();
            bundle.putString("subCategory", "RoutineVideos");
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.drawer_layout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


    private void showRoutine(final String category) {
        videolist.clear();

        if(category.equals("")) {

            mDatabaseRef.child("VIDEOS").limitToLast(10).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    int p=0;
//                Log.d(TAG,dataSnapshot.getValue().toString()+"");
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        VideoModel model = ds.getValue(VideoModel.class);
                        if (model.getSub_category() != null && model.getSub_category().equals("ROUTINE")) {
                            videolist.add(0, model);
                        }
                        if(p==0) {
                            tempkey = model.getVideoId();
                        }
                        p++;

                    }
                    if(videolist.size()==0)
                    {
                        mLastKey=null;
                    }
                    else
                    {
                        mLastKey = videolist.get(videolist.size() - 1).getVideoId();
                    }

                    mAdapter.setData(videolist);
                    //    Collections.reverse(videolist);
                    Log.d(TAG, videolist.size() + "  mmm  ");
                    Log.d(TAG, mLastKey + " ooo ");
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setAdapter(mAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
        {

            mDatabaseRef.child("VIDEOS").limitToLast(10).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        VideoModel model = ds.getValue(VideoModel.class);
                        if (model.getSub_category() != null && model.getSub_category().equals("ROUTINE") && model.getCategory().equals(category)) {
                            videolist.add(0, model);
                        }
                    }
                    if(videolist.size()==0)
                    {
                        mLastKey=null;
                    }
                    else
                    {
                        mLastKey = videolist.get(videolist.size() - 1).getVideoId();
                    }
                    mAdapter.setData(videolist);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setAdapter(mAdapter);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });
        }
    }



    private void showRoutineOld(final String category) {
        videolist.clear();
    ;
        if(category.equals("")) {
            mDatabaseRef.child("VIDEOS").limitToFirst(10).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        VideoModel model = ds.getValue(VideoModel.class);
                        if (model.getSub_category() != null && model.getSub_category().equals("ROUTINE")) {
                            videolist.add( model);
                        }
                    }
                    if(videolist.size()==0)
                    {
                        mLastKey=null;
                    }
                    else
                    {
                        mLastKey = videolist.get(videolist.size() - 1).getVideoId();
                    }
                    mAdapter.setData(videolist);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setAdapter(mAdapter);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
        }
        else
        {
            mDatabaseRef.child("VIDEOS").limitToFirst(10).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        VideoModel model = ds.getValue(VideoModel.class);
                        if (model.getSub_category() != null && model.getSub_category().equals("ROUTINE") && model.getCategory().equals(category)) {
                            videolist.add(model);
                        }
                    }
                    if(videolist.size()==0)
                    {
                        mLastKey=null;
                    }
                    else
                    {
                        mLastKey = videolist.get(videolist.size() - 1).getVideoId();
                    }
                    mAdapter.setData(videolist);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setAdapter(mAdapter);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });
        }
    }


    private void showRoutinelowPrice(final String category) {
        videolist.clear();

        Query query = mDatabaseRef.child("VIDEOS").orderByChild("price").limitToFirst(10);
        if(category.equals("")) {

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        VideoModel model = ds.getValue(VideoModel.class);
                        if (model.getSub_category() != null && model.getSub_category().equals("ROUTINE")) {
                            videolist.add( model);
                        }
                    }
                    if(videolist.size()==0)
                    {
                        mLastKey=null;
                    }
                    else
                    {
                        mLastKey = videolist.get(videolist.size() - 1).getVideoId();
                    }
                    mAdapter.setData(videolist);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setAdapter(mAdapter);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
        }
        else
        {
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        VideoModel model = ds.getValue(VideoModel.class);
                        if (model.getSub_category() != null && model.getSub_category().equals("ROUTINE") && model.getCategory().equals(category)) {
                            videolist.add(model);
                        }
                    }
                    if(videolist.size()==0)
                    {
                        mLastKey=null;
                    }
                    else
                    {
                        mLastKey = videolist.get(videolist.size() - 1).getVideoId();
                    }
                    mAdapter.setData(videolist);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setAdapter(mAdapter);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });
        }
    }


    private void showRoutineHighPrice(final String category) {
        videolist.clear();

        Query query = mDatabaseRef.child("VIDEOS").orderByChild("price").limitToLast(10);
        if(category.equals("")) {
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        VideoModel model = ds.getValue(VideoModel.class);
                        if (model.getSub_category() != null && model.getSub_category().equals("ROUTINE")) {
                            videolist.add( model);
                        }
                    }
                    if(videolist.size()==0)
                    {
                        mLastKey=null;
                    }
                    else
                    {
                        mLastKey = videolist.get(videolist.size() - 1).getVideoId();
                    }
                    mAdapter.setData(videolist);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setAdapter(mAdapter);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
        }
        else
        {
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        VideoModel model = ds.getValue(VideoModel.class);
                        if (model.getSub_category() != null && model.getSub_category().equals("ROUTINE") && model.getCategory().equals(category)) {
                            videolist.add(model);
                        }
                    }
                    if(videolist.size()==0)
                    {
                        mLastKey=null;
                    }
                    else
                    {
                        mLastKey = videolist.get(videolist.size() - 1).getVideoId();
                    }
                    mAdapter.setData(videolist);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setAdapter(mAdapter);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });
        }
    }
}
