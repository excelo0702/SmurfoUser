package com.example.chhots.ui.notifications;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chhots.R;
import com.example.chhots.category_view.routine.routine;
import com.example.chhots.onBackPressed;
import com.example.chhots.ui.Dashboard.HistoryAdapter;
import com.example.chhots.ui.home.HomeFragment;
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

public class NotificationsFragment extends Fragment implements onBackPressed {



    public NotificationsFragment() {
        // Required empty public constructor
    }


    private RecyclerView recyclerView;
    private NotificationAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<NotificationModel> list;


    private DatabaseReference mDatabaseRef;
    private static final String TAG = "Notification";
    private FirebaseAuth auth;
    private FirebaseUser user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        list = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_notification);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        showNotification();



        return view;
    }

    private void showNotification() {
        list.clear();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("NOTIFICATION");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Log.d(TAG,ds.getValue()+"");
                    NotificationModel model = ds.getValue(NotificationModel.class);
                    list.add(model);
                }
                Collections.reverse(list);
                mAdapter = new NotificationAdapter(list,getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.drawer_layout,fragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        setFragment(new HomeFragment());
    }

}


