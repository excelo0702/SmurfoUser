package com.example.chhots;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chhots.category_view.routine.RoutineAdapter;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardView>{


    public LeaderboardAdapter() {
    }

    List<LeaderboardModel> list;
    private Context context;

    public LeaderboardAdapter(List<LeaderboardModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public LeaderboardView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_leaderboard_item,parent,false);
        return new LeaderboardView(view);    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardView holder, int position) {
        holder.username.setText(list.get(position).getName());
        holder.points.setText(list.get(position).getPoints());
    }

    public void setData(List<LeaderboardModel> list){
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LeaderboardView extends RecyclerView.ViewHolder{

        TextView username,points;

        public LeaderboardView(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.userName_leaderboard);
            points = itemView.findViewById(R.id.points_leaderboard);
        }
    }
}
