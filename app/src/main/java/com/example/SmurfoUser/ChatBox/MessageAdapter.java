package com.example.SmurfoUser.ChatBox;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SmurfoUser.R;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyHolder>{


    private Context context;
    private List<MessageModel> list;
    private String TAG = "MessageAdapter";
    private OnItemClickListener listener;
    FirebaseUser user;

    public MessageAdapter(Context context, List<MessageModel> list, OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if(viewType == 0)
        {
            view = LayoutInflater.from(context).inflate(R.layout.raw_chat_item_right,parent,false);

        }
        else
        {
            view = LayoutInflater.from(context).inflate(R.layout.raw_chat_item_left,parent,false);

        }
        return new MyHolder(view);
    }

    public void setData(List<MessageModel>list)
    {
        this.list=list;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.bind(list.get(position),listener);
        MessageModel model = list.get(position);
        Log.d(TAG,"vbn");
        if(model.getFlag()==0)
        {
            holder.message.setText(model.getMessage());
            holder.time.setText(model.getTime());
            holder.playerView.getLayoutParams().height=0;
            holder.playerView.getLayoutParams().width = 0;
        }
        else
        {
            holder.message_left.setText(model.getMessage());
            holder.time_left.setText(model.getTime());
            holder.playerView.getLayoutParams().height=0;
            holder.playerView.getLayoutParams().width = 0;

        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        public TextView message_left,message,time_left,time;
        public PlayerView playerView;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG,"fghjkk");
            message = itemView.findViewById(R.id.show_message);
            message_left = itemView.findViewById(R.id.show_message_left);
            time_left = itemView.findViewById(R.id.show_message_left_date);
            time = itemView.findViewById(R.id.show_message_time);
            playerView = itemView.findViewById(R.id.video_view_chat);

        }

        public void bind(final MessageModel model,final OnItemClickListener listener)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(model);
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).getFlag()==0)
        {
            //sender
            return 0;
        }
        else
        {
            //reciever
            return 1;
        }

    }

    public interface OnItemClickListener{
        void onItemClick(MessageModel model);
    }



}
