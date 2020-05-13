package com.example.chhots.ui.Dashboard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chhots.R;
import com.example.chhots.See_Video;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{

    private static final String TAG = "HistoryAdapter";
    private  List<HistoryModel> historylist;
    private Context context;

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
        holder.value = historylist.get(position).getUrl();
    }

    @Override
    public int getItemCount() {
        return historylist.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{

        public TextView title,dexription;
        String value;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.history_username);
            dexription = itemView.findViewById(R.id.history_video_title);

            dexription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    setFragment(new See_Video());
                    Fragment fragment = new See_Video();




                }
            });
        }

        public void setFragment(Fragment fragment)
        {
            Bundle bundle = new Bundle();
            bundle.putString("videoId",value);
            fragment.setArguments(bundle);
            Log.d(TAG+"  kk ",value);

            FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.dashboard_layout,fragment);
            fragmentTransaction.commit();

        }
    }
}
