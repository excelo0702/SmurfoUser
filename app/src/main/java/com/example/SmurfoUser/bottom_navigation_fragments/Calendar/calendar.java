package com.example.SmurfoUser.bottom_navigation_fragments.Calendar;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.SmurfoUser.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class calendar extends Fragment {


    public calendar() {
        // Required empty public constructor
    }

    private TextView date;
    private CalendarView calendarView;
    private RecyclerView recyclerView;
    private CalendarAdapter mAdapter;
    private List<CalendarModel> list;
    LinearLayoutManager mLayoutManager;

    private DatabaseReference databaseReference;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        date = view.findViewById(R.id.date_view);
        calendarView = view.findViewById(R.id.calender);
        recyclerView = view.findViewById(R.id.event_list);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        list = new ArrayList<>();
        mAdapter = new CalendarAdapter(list,getContext());

        databaseReference = FirebaseDatabase.getInstance().getReference();


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                String d = String.valueOf(year)+" "+String.valueOf(month)+" "+String.valueOf(day);
                date.setText(d);

                databaseReference.child("CALENDAR").child(d)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                list.clear();
                                for(DataSnapshot ds: dataSnapshot.getChildren())
                                {
                                    CalendarModel model = ds.getValue(CalendarModel.class);
                                    list.add(model);
                                }
                                mAdapter.setData(list);
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setAdapter(mAdapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (menu != null) {
            final MenuItem activitySearchMenu = menu.findItem(R.id.action_search);
            activitySearchMenu.setVisible(false);
        }
    }

}
