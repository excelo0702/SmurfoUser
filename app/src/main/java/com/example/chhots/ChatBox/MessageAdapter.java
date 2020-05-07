package com.example.chhots.ChatBox;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chhots.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyHolder>{


    private Context context;
    private List<MessageModel> list;
    private String TAG = "MessageAdapter";
    FirebaseUser user;

    public MessageAdapter(Context context, List<MessageModel> list) {
        this.context = context;
        this.list = list;
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

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        MessageModel model = list.get(position);
        Log.d(TAG,"vbn");
        holder.message.setText(model.getMessage());
    }
    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        public TextView message;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG,"fghjkk");
            message = itemView.findViewById(R.id.show_message);
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
}
