package com.example.chhots.category_view.Contest;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chhots.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class form_contest extends Fragment {


    public form_contest() {
        // Required empty public constructor
    }


    TextView register;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form_contest, container, false);

        register = (TextView)view.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contest fragment = new contest();
                getFragmentManager().beginTransaction().replace(R.id.drawer_layout,fragment)
                        .commit();
            }
        });

        return view;
    }

}
