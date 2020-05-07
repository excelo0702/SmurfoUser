package com.example.chhots.category_view.routine;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chhots.R;
import com.example.chhots.See_Video;
import com.example.chhots.bottom_navigation_fragments.Explore.VideoModel;

import java.util.List;

import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;


/*
 * RecyclerView adapter that renders local video tracks to a VideoView and TextView.
 */


public class VideoAdapter extends
        RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private static final String TAG = "VideoViewRecAdapter";
    private  OnItemClickListener mListener;

    private final List<VideoModel> localVideoTracks;
    private Context context;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public VideoAdapter(List<VideoModel> localVideoTracks, Context context) {
        this.localVideoTracks = localVideoTracks;
        this.context = context;
    }

    @Override
    public void onViewAttachedToWindow(VideoViewHolder holder) {
        Log.d(TAG, "onViewAttachedToWindow");
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(VideoViewHolder holder) {
        Log.d(TAG, "onViewDetachedFromWindow");
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewRecycled(VideoViewHolder holder) {
        Log.d(TAG, "onViewRecycled");
        super.onViewRecycled(holder);
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View view = LayoutInflater.from(context)
                .inflate(R.layout.raw_routine_item, null);

        return new VideoViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");
        VideoModel current = localVideoTracks.get(position);
        holder.title.setText(current.getTitle());
        holder.downvote.setText(String.valueOf(current.getDownvote()));
        holder.upvote.setText(String.valueOf(current.getUpvote()));
        holder.views.setText(String.valueOf(current.getViews()));
        holder.value = current.getVideoId();
        Log.d(TAG,current.getTitle()+"  "+current.getUri());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount");
        return localVideoTracks.size();
    }
    /*
     * View holder that hosts the video view proxy and a text view
     */
    public class VideoViewHolder extends RecyclerView.ViewHolder {

        public TextView title,upvote,downvote,comments,share,views;
        public ImageView upvote_icon,downvote_icon,comment_icon,share_icon;
        public ImageView videoview;
        String value;
        VideoViewHolder(View view, final OnItemClickListener listener) {
            super(view);

            title = view.findViewById(R.id.video_adapter_title);
            upvote = view.findViewById(R.id.video_likess);
            downvote = view.findViewById(R.id.video_downvotee);
            share = view.findViewById(R.id.video_sharee);
            comments = view.findViewById(R.id.video_commentt);
            views = view.findViewById(R.id.video_views);
            upvote_icon = view.findViewById(R.id.video_likes);
            downvote_icon = view.findViewById(R.id.video_downvote);
            share_icon = view.findViewById(R.id.video_share);
            comment_icon = view.findViewById(R.id.video_comment);
            videoview = view.findViewById(R.id.video_view_item);
            videoview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,title.getText().toString()+" jj ",Toast.LENGTH_SHORT).show();

                    // TODO: check subscription of user


                    Fragment fragment = new See_Video();
                    Bundle bundle = new Bundle();
                    bundle.putString("VideoId", value);
                    fragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.drawer_layout,fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });

         /*   videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mP) {
                    mP.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                        @Override
                        public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {

                            mc = new MediaController(getContext());
                            videoview.setMediaController(mc);
                            mc.setAnchorView(videoview);
                        }
                    });
                }
            });
            videoview.start();*/

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                    {
                        int position = getAdapterPosition();
                        listener.onItemClick(position);
                    }
                }
            });

        }
    }
}
