package com.example.chhots.category_view.Courses;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chhots.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyView> {

    private List<CourseThumbnail> list;
    private Context context;
    private final String TAG = "HOrizontalAdapter";

    public HorizontalAdapter(List<CourseThumbnail> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HorizontalAdapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_courses_item,parent,false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalAdapter.MyView holder, int position) {
        holder.CourseName.setText(list.get(position).getCourseName());
        holder.CourseCategory.setText(list.get(position).getCourseName());
        Picasso.get().load(Uri.parse(list.get(position).getCourseImage())).into(holder.image);
        holder.courseId = list.get(position).getCourseId();

        Log.d(TAG,"fghjkk");

    }

    @Override
    public int getItemCount() {


        Log.d(TAG+" ppp ",list.size()+"");

        return list.size();
    }

    public class MyView
            extends RecyclerView.ViewHolder {

        // Text View
        TextView CourseName,CourseCategory;
        ImageView image;
        String courseId;
        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view)
        {
            super(view);
            image = view.findViewById(R.id.raw_image_course);
            CourseName =view.findViewById(R.id.raw_course_name);
            CourseCategory = view.findViewById(R.id.raw_dance_form);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new course_view();
                    Bundle bundle = new Bundle();
                    bundle.putString("courseId",courseId);
                    fragment.setArguments(bundle);

                    FragmentTransaction fragmentTransaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.drawer_layout,fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });



        }
    }
}
