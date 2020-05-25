package com.example.chhots.ui.Dashboard.ApproveVideo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chhots.ChatBox.ChatWithInstructor;
import com.example.chhots.ChatBox.ChatWithUser;
import com.example.chhots.R;

import java.util.List;

public class ChatPeopleAdapter extends RecyclerView.Adapter<ChatPeopleAdapter.MyView>{


    public ChatPeopleAdapter() {
    }

    private List<ChatPeopleModel> list;
    private Context context;
    private String routineId;

    public ChatPeopleAdapter(List<ChatPeopleModel> list, Context context,String routineId) {
        this.list = list;
        this.context = context;
        this.routineId = routineId;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_chat_people_list,parent,false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        holder.name.setText(list.get(position).getUserName());
   //     Picasso.get().load(Uri.parse(list.get(position).getuserImageurl())).into(holder.image);
        holder.peopleId = list.get(position).getUserId();
    }

    public void setData(List<ChatPeopleModel> list)
    {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyView extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView name;
        String peopleId;

        public MyView(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.people_profile);
            name = itemView.findViewById(R.id.people_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChatWithInstructor.class);
                    intent.putExtra("category","INSTRUCTOR");
                    intent.putExtra("routineId",routineId);
                    intent.putExtra("peopleId",peopleId);
                    context.startActivity(intent);
                }
            });


        }
    }
}
