package com.example.SmurfoUser.category_view.Courses;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SmurfoUser.Login;
import com.example.SmurfoUser.R;
import com.example.SmurfoUser.bottom_navigation_fragments.Explore.See_Video;
import com.example.SmurfoUser.SubscriptionModel;
import com.example.SmurfoUser.UserClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyView> {

    private List<CourseThumbnail> list;
    private Context context;
    private final String TAG = "HorizontalAdapter";

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
        holder.thumbnail = list.get(position).getCourseImage();
        holder.instructorId = list.get(position).getInstructorId();
        Log.d(TAG,"fghjkk");

    }

    public void setData(List<CourseThumbnail> list)
    {
        this.list = list;
    }

    @Override
    public int getItemCount() {


        Log.d(TAG+" ppp ",list.size()+"");

        return list.size();
    }

    public class MyView
            extends RecyclerView.ViewHolder {
        int[] p = {1};

        // Text View
        TextView CourseName,CourseCategory;
        ImageView image;
        String courseId,instructorId,thumbnail;
        FirebaseUser user;
        DatabaseReference mDatabaseReference;
        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view)
        {
            super(view);
            image = view.findViewById(R.id.raw_image_course);
            CourseName =view.findViewById(R.id.raw_course_name);
            CourseCategory = view.findViewById(R.id.raw_dance_form);

            user = FirebaseAuth.getInstance().getCurrentUser();
            mDatabaseReference = FirebaseDatabase.getInstance().getReference();

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (user == null) {
                        Toast.makeText(context, "Login First", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, Login.class);
                        context.startActivity(intent);
                    } else {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                //p=checkSubscription();
                                p[0] = 0;
                                if (p[0] == 0) {
                                    p[0] = checkPurchased();
                                }
                            }
                        },5000);


                        if (p[0] == 0) {
                            Fragment fragment = new course_purchase_view();
                            Bundle bundle = new Bundle();
                            bundle.putString("courseId", courseId);
                            fragment.setArguments(bundle);
                            FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.drawer_layout, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    }
                }
            });



        }


        public int checkSubscription()
        {
            Log.d(TAG," pqq ");
            final int[] flag = new int[1];
            mDatabaseReference.child("COURSESUBSCRIPTION").child(user.getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds: dataSnapshot.getChildren())
                            {
                                Log.d(TAG,ds.getValue()+"");

                                SubscriptionModel model = ds.getValue(SubscriptionModel.class);
                                if(model.getVideoId().equals(courseId))
                                {
                                    Log.d(TAG," pqq ");
                                    flag[0] =1;
                                    return;
                                }

                            }
                            if(flag[0]==1)
                            {
                                Fragment fragment = new See_Video();
                                Bundle bundle = new Bundle();
                                bundle.putString("videoId", courseId);
                                fragment.setArguments(bundle);
                                FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.drawer_layout, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
            if(flag[0]==1)
                return 1;
            return 0;
        }

        public int checkPurchased()
        {
            Log.d(TAG," pqq ");
            final int[] flag = new int[1];
            mDatabaseReference.child("USERS").child(user.getUid()).child("courses")
                    .addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG,dataSnapshot.getValue()+"");

                            for(DataSnapshot ds: dataSnapshot.getChildren())
                            {
                                Log.d(TAG,ds.getValue()+"");

                                UserClass model = ds.getValue(UserClass.class);
                                if(model.getCourseId().equals(courseId))
                                {
                                    Log.d(TAG," peee ");
                                    flag[0] =1;
                                    Log.d(TAG,flag[0]+" oo ");
                                }
                            }
                            if(flag[0]==1) {
                                Fragment fragment = new course_view();
                                Bundle bundle = new Bundle();
                                bundle.putString("courseId", courseId);
                                bundle.putString("thumbnail",thumbnail);
                                bundle.putString("instructorId",instructorId);
                                fragment.setArguments(bundle);
                                FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.drawer_layout, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                                return;
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
            if(flag[0]==1)
            {
                return 1;
            }

            //TODO: handler for wait

            return 0;
        }





    }
}
