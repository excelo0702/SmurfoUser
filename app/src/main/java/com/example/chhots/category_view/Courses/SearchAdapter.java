package com.example.chhots.category_view.Courses;

import android.content.Context;
import android.content.Intent;
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

import com.example.chhots.Login;
import com.example.chhots.R;
import com.example.chhots.See_Video;
import com.example.chhots.SubscriptionModel;
import com.example.chhots.UserClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{
    public SearchAdapter() {
    }

    private List<CourseThumbnail> list;
    private Context context;
    private final String TAG = "CoursesSearchAdapter";

    public SearchAdapter(List<CourseThumbnail> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_search_item,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.courseName.setText(list.get(position).getCourseName());
        holder.courseId = list.get(position).getCourseId();
        holder.thumbnail = list.get(position).getCourseImage();
        holder.instructorId = list.get(position).getInstructorId();
    }


    public void setData(List<CourseThumbnail> list)
    {
        this.list = list;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder{
        int p = 1;

        // Text View
        TextView courseName;
        String courseId,instructorId,thumbnail;
        FirebaseUser user;
        DatabaseReference mDatabaseReference;


        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);


            user = FirebaseAuth.getInstance().getCurrentUser();
            mDatabaseReference = FirebaseDatabase.getInstance().getReference();

            courseName = itemView.findViewById(R.id.search_text);
            itemView.setOnClickListener(new View.OnClickListener() {
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
                                p = 0;
                                if (p == 0) {
                                    p = checkPurchased();
                                }
                            }
                        }, 2000);

                    }
                    if (p == 0) {
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
