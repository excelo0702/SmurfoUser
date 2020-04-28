package com.example.chhots.bottom_navigation_fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chhots.R;
import com.example.chhots.category_view.routine.routine;
import com.example.chhots.category_view.routine.routine_view;

/**
 * A simple {@link Fragment} subclass.
 */
public class trending extends Fragment {


    public trending() {
        // Required empty public constructor
    }


    String[] name = {
            "Song" ,
            "Song" ,
            "Song" ,
            "Song" ,
            "Song" ,
            "Song" ,
            "Song" ,

    };
    String[] danceform = {
            "Instructor" ,
            "Instructor" ,
            "Instructor" ,
            "Instructor" ,
            "Instructor" ,
            "Instructor" ,
            "Instructor" ,
    };;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_trending, container, false);

        listview = (ListView)view.findViewById(R.id.list_trendind_view);

        Myadapter myadapter = new Myadapter(getActivity(),name,danceform,imageId);

        listview.setAdapter(myadapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                routine_view fragment = new routine_view();
                Bundle args = new Bundle();
                args.putString("name",name[i]);
                args.putString("danceform",danceform[i]);
                args.putInt("image",imageId[i]);
                fragment.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment)
                        .commit();
            }
        });



        return view;    }

    class Myadapter extends ArrayAdapter<String> {
        Context context;
        String[] name;
        String[] description;
        int imgs[];


        public Myadapter(Context context, String[] name, String[] description, int[] imgs) {
            super(context,R.layout.raw_trending_item,R.id.name_trending,name);
            this.context = context;
            this.name = name;
            this.description = description;
            this.imgs = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.raw_trending_item,parent,false);
            ImageView imageView = row.findViewById(R.id.imageTrending);
            TextView title = row.findViewById(R.id.name_trending);
            TextView desc = row.findViewById(R.id.description_trending);


            imageView.setImageResource(imgs[position]);
            title.setText(name[position]);
            desc.setText(description[position]);




            return row;
        }
    }



}
