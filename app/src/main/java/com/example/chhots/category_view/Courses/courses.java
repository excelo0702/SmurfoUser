package com.example.chhots.category_view.Courses;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chhots.Login;
import com.example.chhots.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class courses extends Fragment {

    public courses() {
        // Required empty public constructor
    }



    ViewPager viewPager;
    List<CourseThumbnail> modelList;
    Button upload_btn;
    private DatabaseReference databaseReference;
    private final String TAG = "Courses123";
    private String history,trending,mostly,for_you;
    private RecyclerView recyclerView1,recyclerView2,recyclerView3,recyclerView4,recyclerView;
    private HorizontalAdapter adapter;
    private Adapter Pageradapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_courses, container, false);
        upload_btn = view.findViewById(R.id.upload_course);
        databaseReference = FirebaseDatabase.getInstance().getReference("");
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),upload_course.class);
                startActivity(intent);
            }
        });


        viewPager = view.findViewById(R.id.courses_viewPager);

        recyclerView1 = view.findViewById(R.id.recycler_recently_course);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));


        recyclerView2 = view.findViewById(R.id.recycler_trending_course);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));


        recyclerView3 = view.findViewById(R.id.recycler_mostlyViewed_course);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));


        recyclerView4 = view.findViewById(R.id.recycler_ForYou_course);
        recyclerView4.setHasFixedSize(true);
        recyclerView4.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        modelList = new ArrayList<>();
        showCoursesImageSlider();
        showRecentCourse();
        showTrendingCourse();
        showMostlyViewedCourse();
        showForYouCourse();


        return view;
    }

    private void showForYouCourse() {
        Query query = databaseReference.child("CoursesThumbnail").orderByKey().limitToFirst(5);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Log.d(TAG+" jjjjjj   ",ds.getValue()+"");
                    CourseThumbnail model = ds.getValue(CourseThumbnail.class);
                    modelList.add(model);
                }
                adapter = new HorizontalAdapter(modelList,getContext());
                recyclerView4.setAdapter(adapter);
                Log.d(TAG,"fghjkk");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void showMostlyViewedCourse() {
        Query query = databaseReference.child("CoursesThumbnail").orderByKey().limitToFirst(5);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Log.d(TAG+" jjjjjj   ",ds.getValue()+"");
                    CourseThumbnail model = ds.getValue(CourseThumbnail.class);
                    modelList.add(model);
                }
                adapter = new HorizontalAdapter(modelList,getContext());
                recyclerView3.setAdapter(adapter);
                Log.d(TAG,"fghjkk");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void showTrendingCourse() {
        Query query = databaseReference.child("CoursesThumbnail").orderByKey().limitToLast(5);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Log.d(TAG+" jjjjjj   ",ds.getValue()+"");
                    CourseThumbnail model = ds.getValue(CourseThumbnail.class);
                    modelList.add(model);
                }
                adapter = new HorizontalAdapter(modelList,getContext());
                recyclerView2.setAdapter(adapter);
                Log.d(TAG,"fghjkk");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void showRecentCourse() {
        Query query = databaseReference.child("CoursesThumbnail").orderByKey().limitToFirst(5);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Log.d(TAG+" jjjjjj   ",ds.getValue()+"");
                    CourseThumbnail model = ds.getValue(CourseThumbnail.class);
                    modelList.add(model);
                }
                adapter = new HorizontalAdapter(modelList,getContext());
                recyclerView1.setAdapter(adapter);
                Log.d(TAG,"fghjkk");

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void showCoursesImageSlider() {
        databaseReference.child("CoursesThumbnail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("IIIImage",dataSnapshot.getChildrenCount()+"");

                modelList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Log.d("IIIImage",ds.getValue().toString());
                    CourseThumbnail model = ds.getValue(CourseThumbnail.class);
                    modelList.add(model);

                }
                Pageradapter = new Adapter(modelList,getContext());
                viewPager.setAdapter(Pageradapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
