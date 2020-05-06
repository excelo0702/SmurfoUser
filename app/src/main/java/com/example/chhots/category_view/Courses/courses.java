package com.example.chhots.category_view.Courses;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chhots.Login;
import com.example.chhots.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class courses extends Fragment {

    public courses() {
        // Required empty public constructor
    }



    List<Model> models;
    LinearLayout recently_viewed;
    ViewPager viewPager;
    Adapter adapter;
    List<ViewPageModel> modelList;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_courses, container, false);

        modelList = new ArrayList<>();

        for(int i=0;i<5;i++)
        {
            modelList.add(new ViewPageModel("Course Name","Dance Form",R.drawable.ic_sentiment_satisfied_black_24dp));
        }

        adapter = new Adapter(modelList,getContext());
        viewPager = (ViewPager)view.findViewById(R.id.courses_viewPager);
        viewPager.setAdapter(adapter);

        LayoutInflater inflater1 = LayoutInflater.from(getContext());

        models = new ArrayList<>();

        for(int i=0;i<5;i++)
        {
            models.add(new Model("Course Name","Dance Form",R.drawable.ic_sentiment_satisfied_black_24dp));
        }

        recently_viewed = (LinearLayout)view.findViewById(R.id.recent_viewed_courses);
        for(int i=0;i<models.size();i++)
        {
            View root = inflater.inflate(R.layout.raw_courses_item,recently_viewed,false);
            TextView name = (TextView)root.findViewById(R.id.raw_course_name);
            TextView dance_form = (TextView)root.findViewById(R.id.raw_dance_form);
            ImageView imageView = (ImageView)root.findViewById(R.id.raw_image_course);

            name.setText(models.get(i).getName());
            dance_form.setText(models.get(i).getDance_form());
            imageView.setImageResource(models.get(i).getImageId());

            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    FirebaseAuth auth = FirebaseAuth.getInstance();

                    if(auth.getCurrentUser()==null)
                    {
                        Toast.makeText(getContext(),"To participate in contest you have to first login",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), Login.class);
                        startActivity(intent);
                    }

                    Fragment fragment = new course_view();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.drawer_layout,fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //pass data too


                }
            });

            recently_viewed.addView(root);
        }


        recently_viewed = (LinearLayout)view.findViewById(R.id.trending_courses);
        for(int i=0;i<models.size();i++)
        {
            View root = inflater.inflate(R.layout.raw_courses_item,recently_viewed,false);
            TextView name = (TextView)root.findViewById(R.id.raw_course_name);
            TextView dance_form = (TextView)root.findViewById(R.id.raw_dance_form);
            ImageView imageView = (ImageView)root.findViewById(R.id.raw_image_course);

            name.setText(models.get(i).getName());
            dance_form.setText(models.get(i).getDance_form());
            imageView.setImageResource(models.get(i).getImageId());

            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new course_view();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.drawer_layout,fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //pass data too


                }
            });

            recently_viewed.addView(root);
        }


        recently_viewed = (LinearLayout)view.findViewById(R.id.most_viewed_courses);
        for(int i=0;i<models.size();i++)
        {
            View root = inflater.inflate(R.layout.raw_courses_item,recently_viewed,false);
            TextView name = (TextView)root.findViewById(R.id.raw_course_name);
            TextView dance_form = (TextView)root.findViewById(R.id.raw_dance_form);
            ImageView imageView = (ImageView)root.findViewById(R.id.raw_image_course);

            name.setText(models.get(i).getName());
            dance_form.setText(models.get(i).getDance_form());
            imageView.setImageResource(models.get(i).getImageId());

            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new course_view();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.drawer_layout,fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //pass data too


                }
            });

            recently_viewed.addView(root);
        }


        recently_viewed = (LinearLayout)view.findViewById(R.id.for_you_courses);
        for(int i=0;i<models.size();i++)
        {
            View root = inflater.inflate(R.layout.raw_courses_item,recently_viewed,false);
            TextView name = (TextView)root.findViewById(R.id.raw_course_name);
            TextView dance_form = (TextView)root.findViewById(R.id.raw_dance_form);
            ImageView imageView = (ImageView)root.findViewById(R.id.raw_image_course);

            name.setText(models.get(i).getName());
            dance_form.setText(models.get(i).getDance_form());
            imageView.setImageResource(models.get(i).getImageId());

            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new course_view();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.drawer_layout,fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //pass data too


                }
            });

            recently_viewed.addView(root);
        }

        return view;
    }


}
