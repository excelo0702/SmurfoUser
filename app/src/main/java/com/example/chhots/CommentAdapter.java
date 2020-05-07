package com.example.chhots;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chhots.ChatBox.MessageModel;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{


    private Context context;
    private List<CommentModel> list;
    private String TAG = "CommentAdapter";

    public CommentAdapter(Context context, List<CommentModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG,"vbn");

        View view = LayoutInflater.from(context).inflate(R.layout.raw_comment_video,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG,"vddddbn");

        CommentModel model = list.get(position);

        holder.comment_user.setText(model.getUser());
        holder.comment.setText(model.getComment());
    }
    @Override
    public int getItemCount() {
        Log.d(TAG,"vbnssssss");

        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView comment,comment_user;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG,"vaaabn");

            comment = itemView.findViewById(R.id.comment_text);
            comment_user = itemView.findViewById(R.id.comment_user);


        }
    }
}
