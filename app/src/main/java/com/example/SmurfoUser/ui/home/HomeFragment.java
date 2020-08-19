package com.example.SmurfoUser.ui.home;

import android.animation.ArgbEvaluator;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.SmurfoUser.R;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    RecyclerView recyclerView;
    HorizontalScrollView horizontalScrollView;

    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    Integer[] colors= null;


    public HomeFragment() { }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        models = new ArrayList<>();
        models.add(new Model("Live","ReadMore",R.drawable.b1));
        models.add(new Model("Routine","ReadMore",R.drawable.b1));
        models.add(new Model("Contest","ReadMore",R.drawable.b1));
        models.add(new Model("Courses","ReadMore",R.drawable.b1));
        models.add(new Model("Booking","ReadMore",R.drawable.b1));




        adapter = new Adapter(models,getContext());
        viewPager = (ViewPager)root.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130,0,120,0);


        final Drawable[] image_temp ={
                ContextCompat.getDrawable(getContext(),R.drawable.b1),
                ContextCompat.getDrawable(getContext(), R.drawable.b1),
                ContextCompat.getDrawable(getContext(),R.drawable.b1),
                ContextCompat.getDrawable(getContext(),R.drawable.b1),
                ContextCompat.getDrawable(getContext(),R.drawable.b1)
        };



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

       //         viewPager.setBackground(image_temp[position]);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        return root;

    }


}