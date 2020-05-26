package com.example.SmurfoUser.ui.Dashboard.HistoryPackage;

import android.content.Context;
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

import com.example.SmurfoUser.R;
import com.example.SmurfoUser.bottom_navigation_fragments.Explore.See_Video;
import com.example.SmurfoUser.category_view.routine.routine_view;

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

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_dashboard,parent,false);
        BottomNavBar = v.findViewById(R.id.bottom_navigation_dashboard);

        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.title.setText(historylist.get(position).getTitle());
        holder.dexription.setText(historylist.get(position).getDescription());
        holder.value = historylist.get(position).getId();
        holder.date.setText(historylist.get(position).getDate());
        holder.category = historylist.get(position).getCategory();
//        Picasso.get().load(Uri.parse(historylist.get(position).getUrl())).resize(70,70).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return historylist.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{

        public TextView title,dexription,date;
        ImageView image;
        String value,category;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.history_username);
            dexription = itemView.findViewById(R.id.history_video_title);
            image = itemView.findViewById(R.id.history_image);
            date = itemView.findViewById(R.id.history_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    BottomNavBar.setVisibility(View.GONE);
                    if(category.equals("Normal")){

                        Bundle bundle = new Bundle();
                        bundle.putString("videoId",value);
                        setFragment(new See_Video(),bundle);
                    }
                    else if(category.equals("Routine"))
                    {

                        Bundle bundle = new Bundle();
                        bundle.putString("category","NotVideoView");
                        bundle.putString("routineId",value);
                        setFragment(new routine_view(),bundle);
                    }
                }
            });
        }

        public void setFragment(Fragment fragment,Bundle bundle)
        {
            fragment.setArguments(bundle);
            Log.d(TAG+"  kk ",value);

            FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.dashboard_layout,fragment);
            fragmentTransaction.commit();

        }
    }
}
