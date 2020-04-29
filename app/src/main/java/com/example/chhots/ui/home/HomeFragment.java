package com.example.chhots.ui.home;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.chhots.R;
import com.example.chhots.category_view.booking;
import com.example.chhots.category_view.contest;
import com.example.chhots.category_view.courses;
import com.example.chhots.category_view.live;
import com.example.chhots.category_view.routine.routine;

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


    public HomeFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);



        models = new ArrayList<>();
        models.add(new Model("Live","ReadMore"));
        models.add(new Model("Routine","ReadMore"));
        models.add(new Model("Contest","ReadMore"));
        models.add(new Model("Personal Artist","ReadMore"));
        models.add(new Model("Courses","ReadMore"));
        models.add(new Model("Booking","ReadMore"));




        adapter = new Adapter(models,getContext());
        viewPager = (ViewPager)root.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130,0,120,0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
           /*     if(position<(adapter.getCount()-1))
                {
                    viewPager.setBackgroundColor((Integer)argbEvaluator.evaluate(positionOffset,colors[position],colors[position+1]));

                }
                else
                {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }*/
            }

            @Override
            public void onPageSelected(int position) {

                switch (position){
                    case 0:
                        Toast.makeText(getContext(),"Booking", LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getContext(),"Live", LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getContext(),"Routine", LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getContext(),"Contest", LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getContext(),"aaloo", LENGTH_SHORT).show();
                        break;



                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



/*
        LinearLayout booking_view = (LinearLayout)root.findViewById(R.id.booking_view);
        booking_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"ggggg", LENGTH_SHORT).show();

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment,new booking());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        LinearLayout live_view = (LinearLayout)root.findViewById(R.id.live_view);
        live_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"ggggg", LENGTH_SHORT).show();

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment,new live());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        LinearLayout routine_view = (LinearLayout)root.findViewById(R.id.routine_view);
        routine_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"ggggg", LENGTH_SHORT).show();

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment,new routine());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        LinearLayout courses_view = (LinearLayout)root.findViewById(R.id.classroom_view);
        courses_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"ggggg", LENGTH_SHORT).show();

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment,new courses());
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }
        });


        LinearLayout contest_view = (LinearLayout)root.findViewById(R.id.contest_view);
        contest_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"ggggg", LENGTH_SHORT).show();

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment,new contest());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
*/


        return root;

    }


}