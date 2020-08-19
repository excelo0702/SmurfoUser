package com.example.SmurfoUser.bottom_navigation_fragments.Calendar;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.SmurfoUser.category_view.Contest.form_contest;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarView> {

    public CalendarAdapter() {
    }

    private List<CalendarModel> list;
    private Context context;
    private final String TAG = "CalendarAdapter1";

    public CalendarAdapter(List<CalendarModel> list, Context context) {
        this.list = list;
        this.context = context;
    }



    @NonNull
    @Override
    public CalendarView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_calendar_list,parent,false);
        return  new CalendarView(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CalendarView holder, int position) {
        Picasso.get().load(Uri.parse(list.get(position).getUrl())).into(holder.img);
        holder.category.setText(list.get(position).getCategory());
        holder.title.setText(list.get(position).getTime());
    }

    public void setData(List<CalendarModel> list)
    {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CalendarView extends RecyclerView.ViewHolder{
        ImageView img;
        TextView category,title;

        public CalendarView(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.circuular_image);
            category = itemView.findViewById(R.id.calendar_category_id);
            title = itemView.findViewById(R.id.calendar_title_id);


        }
    }
}
