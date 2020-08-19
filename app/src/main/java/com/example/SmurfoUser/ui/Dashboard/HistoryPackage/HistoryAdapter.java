package com.example.SmurfoUser.ui.Dashboard.HistoryPackage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SmurfoUser.R;
import com.example.SmurfoUser.bottom_navigation_fragments.Explore.See_Video;
import com.example.SmurfoUser.category_view.Courses.course_purchase_view;
import com.example.SmurfoUser.category_view.routine.routine_purchase;
import com.example.SmurfoUser.category_view.routine.routine_view;
import com.example.SmurfoUser.ui.Dashboard.MyRoutinePackage.MyRoutineAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{

    private static final String TAG = "HistoryAdapter";
    private  List<HistoryModel> historylist;
    private Context context;
    View BottomNavBar;

    public HistoryAdapter(List<HistoryModel> historylist, Context context) {
        this.historylist = historylist;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_history_item,parent,false);


        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.title.setText(historylist.get(position).getTitle());
        holder.dexription.setText(historylist.get(position).getDescription());
        holder.value = historylist.get(position).getId();
        holder.date.setText(historylist.get(position).getDate());
        holder.category = historylist.get(position).getCategory();
        holder.instructorId = historylist.get(position).getDescription();
        holder.thumbnail = historylist.get(position).getUrl();
    }

    @Override
    public int getItemCount() {
        return historylist.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{

        public TextView title,dexription,date;
        ImageView image,three_dot;
        String value,category,instructorId,thumbnail;
        FirebaseUser user;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.history_username);
            dexription = itemView.findViewById(R.id.history_video_title);
            date = itemView.findViewById(R.id.history_date);
            three_dot = itemView.findViewById(R.id.three_dot_history);
            user = FirebaseAuth.getInstance().getCurrentUser();

            three_dot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(three_dot);
                }
            });



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(category.equals("Explore")){

                        Bundle bundle = new Bundle();
                        bundle.putString("videoId",value);
                        setFragment(new See_Video(),bundle);
                    }
                    else if(category.equals("Routine"))
                    {
                        routinePurchase();
                    }
                    else
                    {
                        coursePurchase();
                    }
                }
            });
        }


        public void coursePurchase()
        {
            Log.d("popop","popop111");
            Fragment fragment = new course_purchase_view();
            Bundle bundle = new Bundle();
            bundle.putString("instructorId", instructorId);
            bundle.putString("courseId", value);
            bundle.putString("thumbnail", thumbnail);
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.drawer_layout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }


        public void routinePurchase(){

            Fragment fragment = new routine_purchase();
            Bundle bundle = new Bundle();
            //  bundle.putString("category","Routine");
            bundle.putString("routineId",value);
            bundle.putString("userId",instructorId);
            //     bundle.putString("cat","Routine");
            //   bundle.putString("planplan","1month");
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.drawer_layout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }


        public void setFragment(Fragment fragment,Bundle bundle)
        {

            fragment.setArguments(bundle);
            Log.d(TAG+"  kk ",value);

            FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.dashboard_layout,fragment);
            fragmentTransaction.commit();

        }


        private void showPopupMenu(View view)
        {
            PopupMenu popup = new PopupMenu(context, view, Gravity.END);
            MenuInflater inflater = popup.getMenuInflater();

            inflater.inflate(R.menu.delete_menu, popup.getMenu());

            //set menu item click listener here
            popup.setOnMenuItemClickListener(new MyMenuItemClickListener(getAdapterPosition()));
            popup.show();
        }

        class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
            int position;

            /**
             * @param position
             */
            MyMenuItemClickListener(int position) {

                this.position = position;
            }

            /**
             * Click listener for popup menu items
             */

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.delete:

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference.child("HISTORY").child(user.getUid()).child(value).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        historylist.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position,historylist.size());
                                        Toast.makeText(context,"Deleted ",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context,"Failed ",Toast.LENGTH_SHORT).show();
                                    }
                                });
                        return true;

                }
                return false;
            }
        }



    }
}
