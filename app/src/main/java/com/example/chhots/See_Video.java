package com.example.chhots;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.VideoView;

import com.example.chhots.ChatBox.ChatWithInstructor;
import com.example.chhots.bottom_navigation_fragments.Explore.VideoModel;
import com.example.chhots.category_view.routine.VideoAdapter;
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
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class See_Video extends Fragment {


    public TextView title,upvote,downvote,comments,share,views;
    public ImageView favorite_icon,share_icon;
    public VideoView videoview;
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

    RecyclerView recyclerView;
    private String videoId;
    FirebaseUser user;

    List<CommentModel> list;




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
        videoview = view.findViewById(R.id.video_view_item_see);
        auth = FirebaseAuth.getInstance();
        chatBtn = view.findViewById(R.id.Chat_button);

        send_comment = view.findViewById(R.id.send_comment);
        camera_comment = view.findViewById(R.id.select_image_content);
        comment_message = view.findViewById(R.id.comment_message);

        user = FirebaseAuth.getInstance().getCurrentUser();
        list = new ArrayList<>();
        recyclerView = view.findViewById(R.id.comments);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChatWithInstructor.class);
                intent.putExtra("instructor_id",instructorId);
                startActivity(intent);
            }
        });

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("");


        Bundle bundle = this.getArguments();
        videoId = bundle.getString("VideoId");

        upvote_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseRef.child("videos").child(videoId).child("upvote").setValue(Integer.parseInt(upvote.getText().toString())+1);
                current.setUpvote(current.getUpvote()+1);
                //enable downvote and disable upvote
                mDatabaseRef.child("favorite").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        mDatabaseRef.child("favorite").child(auth.getCurrentUser().getUid()).setValue(current);
                        upvote_icon.setEnabled(false);
                        downvote_icon.setEnabled(true);
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
                mDatabaseRef.child("videos").child(videoId).child("upvote").setValue(Integer.parseInt(upvote.getText().toString())-1);
                current.setUpvote(current.getUpvote()-1);

                upvote_icon.setEnabled(true);
                downvote_icon.setEnabled(false);
                Query applesQuery = mDatabaseRef.child("favorite").child(auth.getCurrentUser().getUid());

                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.v(TAG,dataSnapshot.getValue().toString());

                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                        Log.v(TAG,"deleteed");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });


            }
        });

        favorite_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendComment();
            }
        });


        mDatabaseRef.child("videos").child(videoId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDatabaseRef.child("videos").child(videoId).child("views").setValue(Integer.parseInt(views.getText().toString())+1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        showVideo();
        showComments();






        return view;
    }

    private void showVideo() {


        mDatabaseRef.child("videos").child(videoId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.getChildrenCount()==0)
                    return;

                current = dataSnapshot.getValue(VideoModel.class);
                instructorId = current.getUser();
                title.setText(current.getTitle());
                upvote.setText(String.valueOf(current.getUpvote()));
                views.setText(String.valueOf(current.getViews()));
                videoview.setVideoURI(Uri.parse(current.getUri()));

                videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mP) {
                        mP.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                            @Override
                            public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {

                                mediaController = new MediaController(getContext());
                                videoview.setMediaController(mediaController);
                                mediaController.setAnchorView(videoview);
                            }
                        });
                    }
                });
                videoview.start();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void showComments() {
        mDatabaseRef.child("comments").child(videoId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Log.d(TAG,ds.getValue().toString());
                    CommentModel model = ds.getValue(CommentModel.class);
                    list.add(model);
                }



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
        mDatabaseRef.child("comments").child(videoId).child(time).setValue(model);
    }

}
