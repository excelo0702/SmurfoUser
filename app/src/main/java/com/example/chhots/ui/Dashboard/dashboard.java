package com.example.chhots.ui.Dashboard;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.chhots.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class dashboard extends Fragment {


    public dashboard() {
        // Required empty public constructor
    }
    
    private Button history,leaderboard;
    BottomNavigationView bottomNavigationView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        setFragment(new dashboard_bottom());

        bottomNavigationView = view.findViewById(R.id.bottom_navigation_dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.dashboard_dashboard:
                        setFragment(new dashboard_bottom());
                        break;

                    case R.id.favorite_dashboard:
                        setFragment(new favorite());
                        break;

                    case R.id.community_dashboard:
                        setFragment(new favorite());
                        break;

                    case R.id.certificates_dashboard:
                        setFragment(new favorite());
                        break;

                    case R.id.approve_videos_dashboard:
                        setFragment(new favorite());
                        break;
                }
                return true;
            }
        });
        return view;
    }


    private void setFragment(Fragment fragment) {

        
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.dashboard_layout,fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        View BottomnavBar = getActivity().findViewById(R.id.bottom_navigation);
        BottomnavBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void onDetach() {
        super.onDetach();

        View BottomnavBar = getActivity().findViewById(R.id.bottom_navigation);
        BottomnavBar.setVisibility(View.VISIBLE);

    }
}
