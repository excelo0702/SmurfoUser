package com.example.chhots.category_view.Courses;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chhots.CommentAdapter;
import com.example.chhots.R;
import com.example.chhots.category_view.Courses.ModelCourseView;
import com.example.chhots.category_view.Courses.video_course;
import com.example.chhots.ui.notifications.NotificationsFragment;
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
public class course_view extends Fragment {


    public course_view() {
        // Required empty public constructor
    }

    ListView listview;
    ArrayList<UploadCourseModel> list;
    TextView about;
    private DatabaseReference databaseReference;
    private String courseId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =   inflater.inflate(R.layout.fragment_course_view, container, false);

        list = new ArrayList<>();

        listview = (ListView)view.findViewById(R.id.list_course_view);
        about = (TextView)view.findViewById(R.id.about_course);
        databaseReference = FirebaseDatabase.getInstance().getReference("");

        Bundle bundle = getArguments();
        courseId = bundle.getString("courseId");
        fetchCourse();


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),list.get(i).getCourseName()+"  oo ",Toast.LENGTH_SHORT).show();

                Fragment fragment = new video_course();

                Bundle bundle = new Bundle();
                bundle.putString("videoURL",list.get(i).getVideoUrl());
                fragment.setArguments(bundle);
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

    private void fetchCourse() {
        databaseReference.child("Courses").child(courseId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Log.d("11111kk",ds.getValue()+"");
                    UploadCourseModel model = ds.getValue(UploadCourseModel.class);
                    list.add(model);
                }
                Myadapter adapter = new Myadapter(list,getContext());
                listview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    class Myadapter extends ArrayAdapter<UploadCourseModel>{
    Context context;
    List<UploadCourseModel> list;


        private class ViewHolder{
        TextView lectureNo,lectureName;
    }

    Myadapter(ArrayList<UploadCourseModel> data,Context context)
    {
        super(context,R.layout.raw_course_view_item,data);
        this.list = data;
        this.context = context;
    }



        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            UploadCourseModel model = getItem(position);
            ViewHolder viewHolder;
            final View result;
            if(convertView == null)
            {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.raw_course_view_item,parent,false);
                viewHolder.lectureNo = convertView.findViewById(R.id.lecture_number_course);
                viewHolder.lectureName = convertView.findViewById(R.id.lecture_name_course);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder)convertView.getTag();
                result = convertView;
            }

            // TODO:sequence no.
            viewHolder.lectureNo.setText(model.getCourseName());
            viewHolder.lectureName.setText(model.getCourseName());

            return convertView;

        }
    }


}
