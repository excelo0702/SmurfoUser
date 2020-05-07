package com.example.chhots.category_view.Courses;

import android.content.Context;
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
import androidx.viewpager.widget.PagerAdapter;

import com.example.chhots.R;

import java.util.List;

public class Adapter extends PagerAdapter {

    private List<ViewPageModel> models;
    private LayoutInflater layoutInflater;
    private Context context;


    public Adapter(List<ViewPageModel> models, Context context) {
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
        img.setImageResource(models.get(position).getImageId());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


             /*

FirebaseAuth auth = FirebaseAuth.getInstance();

                if(auth.getCurrentUser()==null)
                {
                    Toast.makeText(getContext(),"To participate in contest you have to first login",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), login.class);
                    context.startActivity(intent);
                }

             Fragment fragment =
                FragmentTransaction fragmentTransaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
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
