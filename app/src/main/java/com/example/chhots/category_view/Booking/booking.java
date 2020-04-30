package com.example.chhots.category_view.Booking;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chhots.R;
import com.example.chhots.category_view.Courses.about_course;

import java.util.ArrayList;
import java.util.List;

public class booking extends Fragment {



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
    ListView listview;

    List<Model> models;

    public booking() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        models = new ArrayList<>();
        for(int i=0;i<name.length;i++)
        {
            models.add(new Model(name[i],danceform[i],imageId[i]));
        }

        listview = (ListView)view.findViewById(R.id.list_booking_view);

        Myadapter myadapter = new Myadapter(getActivity(),name,danceform,imageId);

        listview.setAdapter(myadapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Fragment fragment = new booking_view();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });



        return view;
    }

    class Myadapter extends ArrayAdapter<String>{
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
