package com.example.chhots.category_view;

import android.content.Context;
import android.net.Uri;
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
import android.widget.Toast;

import com.example.chhots.R;

public class courses extends Fragment {

    public courses() {
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
    ListView listview;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_courses, container, false);

        listview = (ListView)view.findViewById(R.id.list_courses_view);

        Myadapter myadapter = new Myadapter(getActivity(),name,danceform,imageId);

        listview.setAdapter(myadapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),name[i],Toast.LENGTH_SHORT).show();
            }
        });



        return view;        }

    class Myadapter extends ArrayAdapter<String> {
        Context context;
        String[] name;
        String[] description;
        int imgs[];


        public Myadapter(Context context, String[] name, String[] description, int[] imgs) {
            super(context,R.layout.raw_classroom_item,R.id.name_courses,name);
            this.context = context;
            this.name = name;
            this.description = description;
            this.imgs = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.raw_classroom_item,parent,false);
            ImageView imageView = row.findViewById(R.id.imageCourses);
            TextView title = row.findViewById(R.id.name_courses);
            TextView desc = row.findViewById(R.id.description_courses);


            imageView.setImageResource(imgs[position]);
            title.setText(name[position]);
            desc.setText(description[position]);




            return row;
        }
    }

}
