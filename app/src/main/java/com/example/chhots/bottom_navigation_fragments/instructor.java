package com.example.chhots.bottom_navigation_fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chhots.R;
import com.example.chhots.ui.notifications.NotificationsFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class instructor extends Fragment {


    public instructor() {
        // Required empty public constructor
    }


    String[] name = {
            "Name" ,
            "Name" ,
            "Name" ,
            "Name" ,
            "Name" ,
            "Name" ,
            "Name" ,

            "Name" ,
            "Name" ,
            "Name" ,
            "Name" ,
    };
    String[] danceform = {
            "Name" ,
            "Name" ,
            "Name" ,
            "Name" ,
            "Name" ,
            "Name" ,
            "Name" ,

            "Name" ,
            "Name" ,
            "Name" ,
            "Name" ,
    };


    int[] imageId = {
            R.drawable.image,
            R.drawable.image,
            R.drawable.image,
            R.drawable.image,
            R.drawable.image,
            R.drawable.image,
            R.drawable.image,

            R.drawable.image,
            R.drawable.image,
            R.drawable.image,
            R.drawable.image,
    };

    ListView listview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_instructor, container, false);
        listview = (ListView)view.findViewById(R.id.list_instructor_view);

        Myadapter myadapter = new Myadapter(getActivity(),name,danceform,imageId);

        listview.setAdapter(myadapter);


        return view;
    }

    class Myadapter extends ArrayAdapter<String> {
        Context context;
        String[] name;
        String[] description;
        int imgs[];


        public Myadapter(Context context, String[] name, String[] description, int[] imgs) {
            super(context, R.layout.raw_instructor_item, R.id.name_instructor, name);
            this.context = context;
            this.name = name;
            this.description = description;
            this.imgs = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.raw_instructor_item, parent, false);
            ImageView imageView = row.findViewById(R.id.imageInstructor);
            TextView title = row.findViewById(R.id.name_instructor);
            TextView desc = row.findViewById(R.id.description_instructor);

            imageView.setImageResource(imgs[position]);
            title.setText(name[position]);
            desc.setText(description[position]);
            return row;
        }
    }


}
