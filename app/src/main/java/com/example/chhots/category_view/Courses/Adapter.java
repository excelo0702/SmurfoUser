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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import com.example.chhots.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends PagerAdapter {

    private List<CourseThumbnail> models;
    private LayoutInflater layoutInflater;
    private Context context;


    public Adapter(List<CourseThumbnail> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {

        Log.d("111111","fghjkllkjhg");
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.raw_course_viewpager_item,container,false);

        TextView course_name,des;
        ImageView img;
        course_name = (TextView)view.findViewById(R.id.raw_course_viewpager_name);
        des = (TextView)view.findViewById(R.id.raw_course_viewpager_description);
        img = (ImageView)view.findViewById(R.id.raw_course_viewpager_image);

        course_name.setText(models.get(position).getCourseName());
        des.setText(models.get(position).getCourseName());
        Picasso.get().load(Uri.parse(models.get(position).getCourseImage())).resize(400,300).into(img);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new course_view();
                Bundle bundle = new Bundle();
                bundle.putString("courseId",models.get(position).getCourseId());
                fragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.drawer_layout,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
