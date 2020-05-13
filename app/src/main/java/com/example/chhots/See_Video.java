package com.example.chhots;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.chhots.ChatBox.ChatWithInstructor;
import com.example.chhots.bottom_navigation_fragments.Explore.VideoModel;
import com.example.chhots.category_view.routine.VideoAdapter;
import com.example.chhots.ui.Dashboard.HistoryModel;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 */
public class See_Video extends Fragment {


    public TextView title,upvote,downvote,comments,share,views;
    public ImageView favorite_icon,share_icon;
    public ImageButton upvote_icon,downvote_icon;
    private ImageView send_comment,camera_comment;
    private MediaController mediaController;
    FirebaseAuth auth;
    private DatabaseReference mDatabaseRef;
    private static final String TAG = "See_Video";
    private FloatingActionButton chatBtn;
    private String instructorId;
    VideoModel current;
    EditText comment_message;
    private Uri videouri;
    CommentAdapter adapter;

    RecyclerView recyclerView;
    private String videoId;
    FirebaseUser user;

    List<CommentModel> list;
    String htitle,hvideoName;


    //exoplayer implementation
    PlayerView playerView;
    SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    ImageView fullScreenButton;
    boolean fullScreen = false;

    public See_Video() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_see__video, container, false);

        title = view.findViewById(R.id.video_adapter_title_see);
        upvote = view.findViewById(R.id.video_likess_see);
        comments = view.findViewById(R.id.video_commentt_see);
        views = view.findViewById(R.id.video_views_see);
        upvote_icon = view.findViewById(R.id.video_likes_see);
        downvote_icon = view.findViewById(R.id.video_downvote_see);
        share_icon = view.findViewById(R.id.video_share_see);
        favorite_icon = view.findViewById(R.id.favorite_comment_see);
        auth = FirebaseAuth.getInstance();

        send_comment = view.findViewById(R.id.send_comment);
        camera_comment = view.findViewById(R.id.select_image_content);
        comment_message = view.findViewById(R.id.comment_message);

        user = FirebaseAuth.getInstance().getCurrentUser();
        list = new ArrayList<>();
        recyclerView = view.findViewById(R.id.comments);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        playerView = view.findViewById(R.id.video_view_item_see);
        fullScreenButton = playerView.findViewById(R.id.exo_fullscreen_icon);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        playerView.setPadding(5,0,5,0);


        fullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FullScreen();
                //      Intent intent = new Intent(getContext(), FloatingWidgetService.class);
                //    intent.putExtra("videoUri",videouri.toString());
                //  getActivity().startService(intent);

            }
        });

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("");


        Bundle bundle = this.getArguments();
        videoId = bundle.getString("videoId");


        upvote_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseRef.child("VIDEOS").child(videoId).child("like").setValue(String.valueOf(Integer.parseInt(upvote.getText().toString())+1));
                current.setLike(String.valueOf(Integer.parseInt(current.getLike())+1));
                //enable downvote and disable upvote
                upvote_icon.setEnabled(false);
                downvote_icon.setEnabled(true);
                mDatabaseRef.child("FAVORITE").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        mDatabaseRef.child("FAVORITE").child(auth.getCurrentUser().getUid()).child(videoId).setValue(current);
                        upvote_icon.setEnabled(false);
                        downvote_icon.setEnabled(true);
                        Toast.makeText(getContext(),"Added to Liked Videos",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        downvote_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabaseRef.child("VIDEOS").child(videoId).child("like").setValue(String.valueOf(Integer.parseInt(upvote.getText().toString())-1));
                current.setLike(String.valueOf(Integer.parseInt(current.getLike())-1));
                //enable downvote and disable upvote
                upvote_icon.setEnabled(true);
                downvote_icon.setEnabled(false);
                mDatabaseRef.child("FAVORITE").child(user.getUid()).child(videoId).removeValue();
                Toast.makeText(getContext(),"Remove From Liked Videos",Toast.LENGTH_SHORT).show();
            }
        });


        send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendComment();
            }
        });




        showVideo();
        showComments();

        return view;
    }

    private void pushHistory() {
        String time = System.currentTimeMillis()+"";
        Log.d(TAG,htitle+hvideoName);
        HistoryModel model = new HistoryModel(htitle,hvideoName,videoId);
        mDatabaseRef.child("HISTORY").child(user.getUid()).child(time).setValue(model);
    }


    private void FullScreen() {
        if(fullScreen)
        {
            fullScreenButton.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_fullscreen_black_24dp));
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);

            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            if (((AppCompatActivity)getActivity()).getSupportActionBar()!=null)
                ((AppCompatActivity)getActivity()).getSupportActionBar().show();
            ((AppCompatActivity)getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)playerView.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = (int)( 330 * getContext().getResources().getDisplayMetrics().density);
            playerView.setLayoutParams(params);
            fullScreen = false;
        }
        else{
            fullScreenButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_fullscreen_black_24dp));

            ((AppCompatActivity)getActivity()).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
            );

            if(((AppCompatActivity)getActivity()).getSupportActionBar() != null){
                ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
            }

            ((AppCompatActivity)getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
            ((AppCompatActivity)getActivity()).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.setMargins(0,0,0,0);
            params.height = params.MATCH_PARENT;

            playerView.setLayoutParams(params);

            View BottomnavBar = getActivity().findViewById(R.id.bottom_navigation);
            BottomnavBar.setVisibility(GONE);


            View NavBar = getActivity().findViewById(R.id.nav_view);
            NavBar.setVisibility(GONE);

            fullScreen = true;
        }
    }


    private void initializePlayer() {

        player = ExoPlayerFactory.newSimpleInstance(getContext());
        playerView.setPlayer(player);

        MediaSource mediaSource = buildMediaSource(videouri);

        player.setPlayWhenReady(playWhenReady);
        //  player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource, false, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getContext(), "exoplayer-codelab");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }



    private void showVideo() {
        mDatabaseRef.child("VIDEOS").child(videoId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.getChildrenCount()==0)
                    return;

                current = dataSnapshot.getValue(VideoModel.class);
                instructorId = current.getUser();
                htitle = current.getTitle();
                hvideoName = current.getTitle();
                title.setText(current.getTitle());
                upvote.setText(String.valueOf(current.getLike()));
                views.setText(String.valueOf(current.getView()));
                videouri=(Uri.parse(current.getUrl()));
                current.setView(String.valueOf(Integer.parseInt(current.getView())+1));

                pushHistory();
                initializePlayer();



            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showComments() {
        Query query =mDatabaseRef.child("COMMENTS").child(videoId).orderByChild("time").limitToLast(100);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Log.d(TAG,ds.getValue().toString());
                    CommentModel model = ds.getValue(CommentModel.class);
                    list.add(model);
                }
                Collections.reverse(list);
                adapter = new CommentAdapter(getContext(),list);
                recyclerView.setAdapter(adapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void sendComment()
    {
        String time = System.currentTimeMillis()+"";
        CommentModel model = new CommentModel(comment_message.getText().toString(),time,user.getUid());
        mDatabaseRef.child("COMMENTS").child(videoId).child(time).setValue(model);
        comment_message.setText("");
    }


}
