package com.example.chhots.category_view.routine;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chhots.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class routine_view extends Fragment {


    public routine_view() {
        // Required empty public constructor
    }

    TextView txt1,txt2;
    ImageView img;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_routine_view, container, false);

        Bundle bundle = this.getArguments();
        String txtt1 = bundle.getString("name");
        String txtt2 = bundle.getString("danceform");
        int imgg = bundle.getInt("image",0);

        Toast.makeText(getContext(),txtt1+txtt2+" fffff",Toast.LENGTH_SHORT).show();

     /*   txt1.setText(txtt1);
        txt2.setText(txtt2);
        img.setImageResource(imgg);
*/
        return view;
    }

}
