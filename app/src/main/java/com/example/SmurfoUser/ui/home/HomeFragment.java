package com.example.SmurfoUser.ui.home;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.SmurfoUser.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    RecyclerView recyclerView;
    HorizontalScrollView horizontalScrollView;

    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();


    public HomeFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);


        models = new ArrayList<>();
        models.add(new Model("Live","ReadMore"));
        models.add(new Model("Routine","ReadMore"));
        models.add(new Model("Contest","ReadMore"));
        models.add(new Model("Courses","ReadMore"));
        models.add(new Model("Booking","ReadMore"));




        adapter = new Adapter(models,getContext());
        viewPager = (ViewPager)root.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130,0,120,0);



        return root;

    }


}