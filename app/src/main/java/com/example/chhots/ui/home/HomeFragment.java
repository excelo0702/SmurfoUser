package com.example.chhots.ui.home;

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

import com.example.chhots.R;
import com.example.chhots.category_view.booking;
import com.example.chhots.category_view.contest;
import com.example.chhots.category_view.courses;
import com.example.chhots.category_view.live;
import com.example.chhots.category_view.routine.routine;

import static android.widget.Toast.LENGTH_SHORT;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    RecyclerView recyclerView;
    HorizontalScrollView horizontalScrollView;

    public HomeFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

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



        return root;

    }


}