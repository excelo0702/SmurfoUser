package com.example.chhots.category_view.Booking;


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

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class booking_view extends Fragment {


    public booking_view() {
        // Required empty public constructor
    }


    String[] name = {"tanish" ,"tanish" ,"tanish" ,"tanish" ,"tanish" ,"tanish" , "mommy"};
    String[] danceform = {"tanish" ,"tanish" ,"tanish" ,"tanish" ,"tanish" ,"tanish" , "mommy"};;
    int[] imageId = {
            R.drawable.image,
            R.drawable.image,
            R.drawable.image,
            R.drawable.image,
            R.drawable.image,
            R.drawable.image,
            R.drawable.image
    };
    ListView listview_judged,listview_workshop,listview_courses;

    List<Model> models;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking_view, container, false);

        listview_judged = (ListView)view.findViewById(R.id.judged);
        listview_workshop = (ListView)view.findViewById(R.id.workshop);
        listview_courses = (ListView)view.findViewById(R.id.courses);

        Myadapter myadapter = new Myadapter(getActivity(),name,danceform,imageId);

        listview_judged.setAdapter(myadapter);
        listview_workshop.setAdapter(myadapter);
        listview_courses.setAdapter(myadapter);

        return view;
    }

    class Myadapter extends ArrayAdapter<String> {
        Context context;
        String[] name;
        String[] description;
        int imgs[];


        public Myadapter(Context context, String[] name, String[] description, int[] imgs) {
            super(context,R.layout.raw_booking_item,R.id.name_booking,name);
            this.context = context;
            this.name = name;
            this.description = description;
            this.imgs = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.raw_booking_item,parent,false);
            ImageView imageView = row.findViewById(R.id.imageBooking);
            TextView title = row.findViewById(R.id.name_booking);
            TextView desc = row.findViewById(R.id.desciption_booking);


            imageView.setImageResource(imgs[position]);
            title.setText(name[position]);
            desc.setText(description[position]);

            return row;
        }
    }


}
