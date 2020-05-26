package com.example.SmurfoUser.category_view.routine;

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

import com.example.SmurfoUser.LoadingDialog;
import com.example.SmurfoUser.Login;
import com.example.SmurfoUser.R;
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

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineView>{


    public RoutineAdapter() {
    }

    private List<RoutineThumbnailModel> list;
    private Context context;
    private final String TAG = "RoutineAdapter1";

    public RoutineAdapter(List<RoutineThumbnailModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RoutineView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_routine_new_item,parent,false);
        return new RoutineView(view);
    }

    public void setData(List<RoutineThumbnailModel> list)
    {
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineView holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.instructor_name.setText(list.get(position).getInstructor_name());
        holder.level.setText(list.get(position).getRoutine_level());
        holder.routine_views.setText(list.get(position).getRoutine_views());
        holder.routineId = list.get(position).getRoutineId();
        Picasso.get().load(Uri.parse(list.get(position).routineThumbnail)).placeholder(R.drawable.smurfoo_dp).into(holder.routine_view_image);
        holder.thumbnail = list.get(position).getRoutineThumbnail();
        holder.instructorId = list.get(position).getInstructorId();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RoutineView extends RecyclerView.ViewHolder{



        TextView title,instructor_name,routine_views,level;
        ImageView routine_view_image;
        String routineId,thumbnail,userId,instructorId;
        FirebaseUser user;
        DatabaseReference mDatabaseReference;
        LoadingDialog loadingDialog;

         public RoutineView(@NonNull View itemView) {
             super(itemView);
             title = itemView.findViewById(R.id.routine_view_title);
             instructor_name = itemView.findViewById(R.id.routine_instructor_name);
             routine_view_image = itemView.findViewById(R.id.routine_view_item);
             routine_views = itemView.findViewById(R.id.routine_views);
             level = itemView.findViewById(R.id.routine_level);
             user = FirebaseAuth.getInstance().getCurrentUser();
             mDatabaseReference = FirebaseDatabase.getInstance().getReference();
             userId = user.getUid();
             loadingDialog = new LoadingDialog(((AppCompatActivity) context));

             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     int p=0;
                     //TODO: add handler
                     if(user==null)
                     {
                         Toast.makeText(context,"Login First",Toast.LENGTH_SHORT).show();
                         Intent intent = new Intent(context, Login.class);
                         context.startActivity(intent);
                     }
               /*      else if(userId==instructorId)
                     {
                         Fragment fragment = new routine_view();
                         Bundle bundle = new Bundle();
                         bundle.putString("category","Routine");
                         bundle.putString("routineId",routineId);
                         fragment.setArguments(bundle);
                         FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                         fragmentTransaction.replace(R.id.drawer_layout, fragment);
                         fragmentTransaction.addToBackStack(null);
                         fragmentTransaction.commit();
                     }*/
                     else
                     {
                         loadingDialog.startLoadingDialog();
                      //   p = checkSubscription();
                         if(p==0)
                         {
                             p = checkPurchased();
                         }
                     }
                     new Handler().postDelayed(new Runnable() {
                         @Override
                         public void run() {
                            loadingDialog.DismissDialog();
                         }
                     },3000);
                     if(p==1)
                     {
                         Fragment fragment = new routine_view();
                         Bundle bundle = new Bundle();
                         bundle.putString("category","Routine");
                         bundle.putString("routineId",routineId);
                         fragment.setArguments(bundle);
                         FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                         fragmentTransaction.replace(R.id.drawer_layout, fragment);
                         fragmentTransaction.addToBackStack(null);
                         fragmentTransaction.commit();
                     }
                     else
                     {
                         Fragment fragment = new routine_purchase();
                         Bundle bundle = new Bundle();
                         bundle.putString("routineId", routineId);
                         bundle.putString("thumbnail", thumbnail);
                         bundle.putString("userId", userId);
                         fragment.setArguments(bundle);
                         FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                         fragmentTransaction.replace(R.id.drawer_layout, fragment);
                         fragmentTransaction.addToBackStack(null);
                         fragmentTransaction.commit();
                     }

                 }
             });

         }


/*
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
                                if(model.getVideoId().equals(value))
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
                                bundle.putString("videoId", value);
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
*/
        public int checkPurchased()
        {
            Log.d(TAG," pqq ");
            final int[] flag = new int[1];
            mDatabaseReference.child("USER_PURCHASED_ROUTINES").child(userId)
                    .addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG,dataSnapshot.getValue()+"");

                            for(DataSnapshot ds: dataSnapshot.getChildren())
                            {
                                Log.d(TAG,ds.getValue()+"");

                                UserClass model = ds.getValue(UserClass.class);
                                if(model.getVideoId().equals(routineId))
                                {
                                    Log.d(TAG," peee ");
                                    flag[0] =1;
                                    Log.d(TAG,flag[0]+" oo ");
                                }
                            }
                            if(flag[0]==1) {
                                Fragment fragment = new routine_view();
                                Bundle bundle = new Bundle();
                                bundle.putString("routineId", routineId);
                                bundle.putString("category","NoVideoView");
                                fragment.setArguments(bundle);
                                FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.drawer_layout, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });

            //TODO: handler for wait

            return 0;
        }



    }
}
