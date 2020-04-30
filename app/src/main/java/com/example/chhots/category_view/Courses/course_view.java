package com.example.chhots.category_view.Courses;


import android.content.Context;
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
import com.example.chhots.category_view.Courses.ModelCourseView;
import com.example.chhots.category_view.Courses.video_course;
import com.example.chhots.ui.notifications.NotificationsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class course_view extends Fragment {


    public course_view() {
        // Required empty public constructor
    }


    String[] name = {
            "5000 KRW OFF coupon has been issued" ,
            "5000 KRW OFF coupon has been issued" ,
            "5000 KRW OFF coupon has been issued" ,
            "5000 KRW OFF coupon has been issued" ,
            "5000 KRW OFF coupon has been issued" ,
            "5000 KRW OFF coupon has been issued" ,

            "5000 KRW OFF coupon has been issued" ,
            "5000 KRW OFF coupon has been issued" ,
            "5000 KRW OFF coupon has been issued"
    };


    String[] time = {
            "2020.04.27 5:05AM",
            "2020.04.27 5:05AM",
            "2020.04.27 5:05AM",
            "2020.04.27 5:05AM",
            "2020.04.27 5:05AM",
            "2020.04.27 5:05AM",

            "2020.04.27 5:05AM",
            "2020.04.27 5:05AM",
            "2020.04.27 5:05AM",
    };

    ListView listview;
    TextView about;
    //List<ModelCourseView> modelList;
    //Myadapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =   inflater.inflate(R.layout.fragment_course_view, container, false);

     /*   modelList = new ArrayList<>();



        for(int i=0;i<20;i++)
        {
            modelList.add(new ModelCourseView("Chapter Name",String.valueOf(i+1)));
        }

        listView = (ListView)view.findViewById(R.id.list_course_view);
        adapter = new Myadapter(getContext(),modelList);
        listView.setAdapter(adapter);

        Toast.makeText(getContext(),String.valueOf(modelList.size()),Toast.LENGTH_LONG).show();


*/


        listview = (ListView)view.findViewById(R.id.list_course_view);
        about = (TextView)view.findViewById(R.id.about_course);

        Myadapter myadapter = new Myadapter(getActivity(),name,time);

        listview.setAdapter(myadapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment fragment = new video_course();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.video_space_course,fragment);
                fragmentTransaction.commit();

            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new about_course();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.about_course_space,fragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

/*
    class Myadapter extends ArrayAdapter<String> {
        Context context;
        List<ModelCourseView> models;


        public Myadapter(Context context, List<ModelCourseView> models) {
            super(context, R.layout.raw_course_view_item);
            this.context = context;
            this.models = models;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.raw_course_view_item, parent, false);
            TextView title = row.findViewById(R.id.chapter_name_course);
            TextView number = row.findViewById(R.id.chapter_number_course);

            title.setText(models.get(position).getName());
            number.setText(models.get(position).getNumber());


            return row;
        }
    }

*/
class Myadapter extends ArrayAdapter<String> {
    Context context;
    String[] name;
    String[] ttime;


    public Myadapter(Context context, String[] name,String[] ttime) {
        super(context, R.layout.raw_course_view_item, R.id.chapter_name_course, name);
        this.context = context;
        this.name = name;
        this.ttime = ttime;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.raw_course_view_item, parent, false);
        TextView title = row.findViewById(R.id.chapter_name_course);
        TextView txttime = row.findViewById(R.id.chapter_number_course);

        title.setText(name[position]);
        txttime.setText(ttime[position]);
        return row;
    }
}


}
