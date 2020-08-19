package com.example.SmurfoUser.ui.Dashboard.ApproveVideo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SmurfoUser.R;
import com.example.SmurfoUser.UserClass;
import com.example.SmurfoUser.category_view.routine.RoutineThumbnailModel;
import com.example.SmurfoUser.category_view.routine.routine_purchase;
import com.example.SmurfoUser.category_view.routine.routine_view;
import com.example.SmurfoUser.ui.Dashboard.MyRoutinePackage.MyRoutineModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ApproveVideoAdapter extends RecyclerView.Adapter<ApproveVideoAdapter.ApproveVideoHolder>{


    public ApproveVideoAdapter() {
    }
    private List<MyRoutineModel> list;
    private Context context;
    private final String TAG = "ApproveVideoAdapter1";

    public ApproveVideoAdapter(List<MyRoutineModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ApproveVideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_approve_video_item,parent,false);
        return new ApproveVideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApproveVideoHolder holder, int position) {

        holder.title.setText(list.get(position).getRoutineName());
        Picasso.get().load(Uri.parse(list.get(position).getRoutineThumbnail())).into(holder.thumbnail);
        holder.routineId = list.get(position).getRoutineId();

        //TODO:CHECK SUBSCRIPTION

    }

    public void setData(List<MyRoutineModel> list)
    {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ApproveVideoHolder extends RecyclerView.ViewHolder{

        ImageView thumbnail;
        TextView title,notify,routineView,delete;
        String routineId,imageURL,userId,instructorId,thumbnailI;
        RelativeLayout relativeLayout;
        FirebaseUser user;
        public ApproveVideoHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.routine_image);
            title = itemView.findViewById(R.id.raw_routine_name);
            notify = itemView.findViewById(R.id.raw_routine_notification);
            relativeLayout = itemView.findViewById(R.id.routine_raw_view);
            routineView = itemView.findViewById(R.id.raw_routine_view);
            delete = itemView.findViewById(R.id.raw_routine_delete);
            user = FirebaseAuth.getInstance().getCurrentUser();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new ChatPeopleList();
                    Bundle bundle = new Bundle();
                    bundle.putString("routineId",routineId);
                    bundle.putString("instructorId",userId);
                    Log.d(TAG,routineId+"  p "+userId);

                    fragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.dashboard_layout,fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });


            routineView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Fragment fragment = new routine_view();
                    Bundle bundle = new Bundle();
                    bundle.putString("category", "Routine");
                    bundle.putString("routineId", routineId);
                    bundle.putString("cat","Routine");
                    bundle.putString("planplan","1month");
                    fragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.drawer_layout, fragment);
                    fragmentTransaction.commit();
                }
            });




        }
/*
        public int checkSubscription()
        {

            //kon sa subscription h uske pas


            Log.d(TAG," pqq ");
            final int[] flag = new int[1];

            final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


            //      mDatabaseReference.child("USER_PURCHASED_ROUTINES").child(userId).child("Individual").child()

            DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();

            mDatabaseReference.child("USER_PURCHASED").child(user.getUid()).child("Routine")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            UserClass model = dataSnapshot.getValue(UserClass.class);

                            //find category
                            if (model != null) {
                                int k = dateDifference(date, model.getDate());
                                if (model.getCategory().equals("1month")) {
                                    if (k > 30) {
                                        //expired
                                        Log.d("popop","popop11");
                                        Fragment fragment = new routine_purchase();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("userId", instructorId);
                                        bundle.putString("routineId", routineId);
                                        bundle.putString("thumbnail", thumbnail);
                                        fragment.setArguments(bundle);
                                        FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.drawer_layout, fragment);
                                        fragmentTransaction.commit();
                                        return;
                                    } else {
                                        //go to routine view
                                        Log.d("popop","popop12");
                                        Fragment fragment = new routine_view();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("category", "Routine");
                                        bundle.putString("routineId", routineId);
                                        bundle.putString("cat","Routine");
                                        bundle.putString("planplan","1month");
                                        fragment.setArguments(bundle);
                                        FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.drawer_layout, fragment);
                                        fragmentTransaction.commit();
                                        return;
                                    }
                                } else if (model.getCategory().equals("6month")) {
                                    if (k > 180) {
                                        Log.d("popop","popop21");
                                        Fragment fragment = new routine_purchase();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("userId", instructorId);
                                        bundle.putString("routineId", routineId);
                                        bundle.putString("thumbnail", thumbnail);
                                        fragment.setArguments(bundle);
                                        FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.drawer_layout, fragment);
                                        fragmentTransaction.commit();
                                        return;
                                    } else {
                                        Log.d("popop","popop22");
                                        Fragment fragment = new routine_view();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("category", "Routine");
                                        bundle.putString("routineId", routineId);
                                        bundle.putString("cat","Routine");
                                        bundle.putString("planplan","6month");
                                        fragment.setArguments(bundle);
                                        FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.drawer_layout, fragment);
                                        fragmentTransaction.commit();
                                        return;
                                    }
                                } else if (model.getCategory().equals("1year")) {
                                    if (k > 365) {Fragment fragment = new routine_purchase();
                                        Log.d("popop","popop31");
                                        Bundle bundle = new Bundle();
                                        bundle.putString("userId", instructorId);
                                        bundle.putString("routineId", routineId);
                                        bundle.putString("thumbnail", thumbnail);
                                        fragment.setArguments(bundle);
                                        FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.drawer_layout, fragment);
                                        fragmentTransaction.commit();

                                        return;
                                    } else {
                                        Log.d("popop","popop42");
                                        Fragment fragment = new routine_view();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("category", "Routine");
                                        bundle.putString("routineId", routineId);
                                        bundle.putString("cat","Routine");
                                        bundle.putString("planplan","1year");
                                        fragment.setArguments(bundle);
                                        FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.drawer_layout, fragment);
                                        fragmentTransaction.commit();
                                        return;
                                    }
                                }
                            }
                            else
                            {
                                //check for full subscription
                                mDatabaseReference.child("USER_PURCHASED").child(user.getUid()).child("Full")
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                UserClass model = dataSnapshot.getValue(UserClass.class);

                                                //find category
                                                if (model != null) {
                                                    int k = dateDifference(date, model.getDate());
                                                    if (model.getCategory().equals("1month")) {
                                                        if (k > 30) {
                                                            //expired
                                                            Log.d("popop","popop11");
                                                            Fragment fragment = new routine_purchase();
                                                            Bundle bundle = new Bundle();
                                                            bundle.putString("userId", instructorId);
                                                            bundle.putString("routineId", routineId);
                                                            bundle.putString("thumbnail", thumbnail);
                                                            fragment.setArguments(bundle);
                                                            FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                                            fragmentTransaction.replace(R.id.drawer_layout, fragment);
                                                            fragmentTransaction.commit();
                                                            return;
                                                        } else {
                                                            //go to routine view
                                                            Log.d("popop","popop12");
                                                            Fragment fragment = new routine_view();
                                                            Bundle bundle = new Bundle();
                                                            bundle.putString("category", "Routine");
                                                            bundle.putString("routineId", routineId);
                                                            bundle.putString("cat","Full");
                                                            bundle.putString("planplan","1month");
                                                            fragment.setArguments(bundle);
                                                            FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                                            fragmentTransaction.replace(R.id.drawer_layout, fragment);
                                                            fragmentTransaction.commit();
                                                            return;
                                                        }
                                                    } else if (model.getCategory().equals("6month")) {
                                                        if (k > 180) {
                                                            Log.d("popop","popop21");
                                                            Fragment fragment = new routine_purchase();
                                                            Bundle bundle = new Bundle();
                                                            bundle.putString("userId", instructorId);
                                                            bundle.putString("routineId", routineId);
                                                            bundle.putString("thumbnail", thumbnail);
                                                            fragment.setArguments(bundle);
                                                            FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                                            fragmentTransaction.replace(R.id.drawer_layout, fragment);
                                                            fragmentTransaction.commit();
                                                            return;
                                                        } else {
                                                            Log.d("popop","popop22");
                                                            Fragment fragment = new routine_view();
                                                            Bundle bundle = new Bundle();
                                                            bundle.putString("category", "Routine");
                                                            bundle.putString("routineId", routineId);
                                                            bundle.putString("cat","Full");
                                                            bundle.putString("planplan","6month");
                                                            fragment.setArguments(bundle);
                                                            FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                                            fragmentTransaction.replace(R.id.drawer_layout, fragment);
                                                            fragmentTransaction.commit();
                                                            return;
                                                        }
                                                    } else if (model.getCategory().equals("1year")) {
                                                        if (k > 365) {Fragment fragment = new routine_purchase();
                                                            Log.d("popop","popop31");
                                                            Bundle bundle = new Bundle();
                                                            bundle.putString("userId", instructorId);
                                                            bundle.putString("routineId", routineId);
                                                            bundle.putString("thumbnail", thumbnail);
                                                            fragment.setArguments(bundle);
                                                            FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                                            fragmentTransaction.replace(R.id.drawer_layout, fragment);
                                                            fragmentTransaction.commit();

                                                            return;
                                                        } else {
                                                            Log.d("popop","popop42");
                                                            Fragment fragment = new routine_view();
                                                            Bundle bundle = new Bundle();
                                                            bundle.putString("category", "Routine");
                                                            bundle.putString("routineId", routineId);
                                                            bundle.putString("cat","Full");
                                                            bundle.putString("planplan","1year");
                                                            fragment.setArguments(bundle);
                                                            FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                                            fragmentTransaction.replace(R.id.drawer_layout, fragment);
                                                            fragmentTransaction.commit();
                                                            return;
                                                        }
                                                    }
                                                }

                                                //check individual subscription
                                                mDatabaseReference.child("USER_PURCHASED").child(user.getUid()).child("Individual")
                                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                UserClass model = dataSnapshot.getValue(UserClass.class);
                                                                if(model!=null)
                                                                {
                                                                    if(model.getId().equals(routineId))
                                                                    {
                                                                        int k = dateDifference(date,model.getDate());
                                                                        if(k>5)
                                                                        {

                                                                            Log.d("popop","popop11");
                                                                            Fragment fragment = new routine_purchase();
                                                                            Bundle bundle = new Bundle();
                                                                            bundle.putString("userId", instructorId);
                                                                            bundle.putString("routineId", routineId);
                                                                            bundle.putString("thumbnail", thumbnail);
                                                                            fragment.setArguments(bundle);
                                                                            FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                                                            fragmentTransaction.replace(R.id.drawer_layout, fragment);
                                                                            fragmentTransaction.commit();
                                                                            return;
                                                                        }
                                                                        else
                                                                        {
                                                                            //go to routine view
                                                                            Log.d("popop","popop12");
                                                                            Fragment fragment = new routine_view();
                                                                            Bundle bundle = new Bundle();
                                                                            bundle.putString("category", "Routine");
                                                                            bundle.putString("routineId", routineId);
                                                                            bundle.putString("cat","Individual");
                                                                            bundle.putString("planplan","5days");
                                                                            fragment.setArguments(bundle);
                                                                            FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                                                            fragmentTransaction.replace(R.id.drawer_layout, fragment);
                                                                            fragmentTransaction.commit();
                                                                            return;
                                                                        }

                                                                    }
                                                                }
                                                                else
                                                                {
                                                                    Log.d("popop","popop55");
                                                                    Fragment fragment = new routine_purchase();
                                                                    Bundle bundle = new Bundle();
                                                                    bundle.putString("userId", instructorId);
                                                                    bundle.putString("routineId", routineId);
                                                                    bundle.putString("thumbnail", thumbnail);
                                                                    fragment.setArguments(bundle);
                                                                    FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                                                    fragmentTransaction.replace(R.id.drawer_layout, fragment);
                                                                    fragmentTransaction.commit();
                                                                    return;
                                                                }

                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });
            return 0;
        }

*/
    }
}
