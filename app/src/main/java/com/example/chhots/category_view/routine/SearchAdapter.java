package com.example.chhots.category_view.routine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.chhots.bottom_navigation_fragments.Explore.VideoModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{

    private static final String TAG = "NotificationViewAdapter";
    private List<VideoModel> list;
    private Context context;

    public SearchAdapter(List<VideoModel> list, Context context) {
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
        holder.search_text.setText(list.get(position).getDescription());
        holder.videoId = list.get(position).getVideoId();
        holder.sub_category = list.get(position).getSub_category();
        holder.thumbnail = list.get(position).getThumbnail();
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public void setData(List<VideoModel> list)
    {
        this.list =list;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder{

        TextView search_text;
        String videoId,sub_category,thumbnail,userId;
        FirebaseUser user;
        DatabaseReference mDatabaseReference;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            search_text = itemView.findViewById(R.id.search_text);
            user = FirebaseAuth.getInstance().getCurrentUser();
            mDatabaseReference = FirebaseDatabase.getInstance().getReference();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (user == null) {
                        Toast.makeText(context, "Login First", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, Login.class);
                        context.startActivity(intent);

                    } else {

                        int p = 1;
                        if (sub_category.equals("ROUTINE")) {
                            // TODO: check subscription of user
                            p = checkSubscription();
                            Log.d(TAG, p + " p ");
                            if (p == 0) {
                                p = checkPurchased();
                                Log.d(TAG, p + " q ");
                            }
                        }
                        if (p == 1) {
                            Fragment fragment = new See_Video();
                            Bundle bundle = new Bundle();
                            bundle.putString("videoId", videoId);
                            fragment.setArguments(bundle);
                            FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.drawer_layout, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                        } else {
                            //Create Pop up to Buy this Video
                            //Redirect to routine_purchase
                            Fragment fragment = new routine_purchase();
                            Bundle bundle = new Bundle();
                            bundle.putString("videoId", videoId);
                            bundle.putString("thumbnail", thumbnail);
                            bundle.putString("instructorId", userId);
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
            mDatabaseReference.child("SUBSCRIPTION").child(user.getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds: dataSnapshot.getChildren())
                            {
                                Log.d(TAG,ds.getValue()+"");

                                SubscriptionModel model = ds.getValue(SubscriptionModel.class);
                                if(model.getVideoId().equals(videoId))
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
                                bundle.putString("videoId", videoId);
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
            mDatabaseReference.child("USERS").child(user.getUid()).child("videos")
                    .addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG,dataSnapshot.getValue()+"");

                            for(DataSnapshot ds: dataSnapshot.getChildren())
                            {
                                Log.d(TAG,ds.getValue()+"");

                                UserClass model = ds.getValue(UserClass.class);
                                if(model.getVideoId().equals(videoId))
                                {
                                    Log.d(TAG," peee ");
                                    flag[0] =1;
                                    Log.d(TAG,flag[0]+" oo ");
                                }
                            }
                            if(flag[0]==1) {
                                Fragment fragment = new See_Video();
                                Bundle bundle = new Bundle();
                                bundle.putString("videoId", videoId);
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

            //TODO: handler for wait

            return 0;
        }

    }
}
