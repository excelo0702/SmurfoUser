package com.example.chhots.category_view.Courses;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.chhots.R;

/**
 * A simple {@link Fragment} subclass.
 */

public class video_course extends Fragment {

    public video_course() {// Required empty public constructor
    }

    private VideoView videoView;
    private MediaController mediaController;
    private static final String TAG = "video_course";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_video_course, container, false);
        videoView = view.findViewById(R.id.video_view_course);

        Bundle bundle = this.getArguments();
        Uri uri = Uri.parse(bundle.getString("videoURL"));
        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mP) {
                mP.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {

                        mediaController = new MediaController(getContext());
                        videoView.setMediaController(mediaController);
                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });
        videoView.start();
        return view;
    }


}
