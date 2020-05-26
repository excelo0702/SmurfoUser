package com.example.SmurfoUser.Services;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.SmurfoUser.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;

public class FloatingWidgetService extends Service {

    public FloatingWidgetService() {
    }


    WindowManager mWindowManager;
    private View mFloatingWidget;
    SimpleExoPlayer exoPlayer;
    PlayerView playerView;

    Uri videoUri;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent!=null)
        {
            String uriStr = intent.getStringExtra("videoUri");
            videoUri = Uri.parse(uriStr);

            if(mWindowManager!= null && mFloatingWidget.isShown() && exoPlayer!=null)
            {
                mWindowManager.removeView(mFloatingWidget);
                mFloatingWidget = null;
                mWindowManager = null;
                exoPlayer.setPlayWhenReady(false);
                exoPlayer.release();
                exoPlayer = null;
            }
            final WindowManager.LayoutParams params;
            mFloatingWidget = LayoutInflater.from(this).inflate(R.layout.custom_pop_up_window,null);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                params = new WindowManager.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT
                );
            }
            else{
                params = new WindowManager.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT
                );
            }

            params.gravity = Gravity.TOP | Gravity.LEFT;
            params.x = 200;
            params.y = 200;

            mWindowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
            mWindowManager.addView(mFloatingWidget,params);

            initializePlayer();

            playerView = (PlayerView)mFloatingWidget.findViewById(R.id.playerView_service);
            ImageView imageViewClose = mFloatingWidget.findViewById(R.id.imageviewDismiss);
            ImageView imageViewmaximise = mFloatingWidget.findViewById(R.id.imageviewMaximise);

            imageViewmaximise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            imageViewClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            playVideo();


            mFloatingWidget.findViewById(R.id.relative_layout_pop_up).setOnTouchListener(new View.OnTouchListener() {

                private int initialx;
                private int initialy;
                private float initialTouchx,initialTouchy;
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction())
                    {
                        case MotionEvent.ACTION_DOWN:
                            initialx = params.x;
                            initialy = params.y;
                            initialTouchx = motionEvent.getRawX();
                            initialTouchy = motionEvent.getRawY();
                            return true;

                        case MotionEvent.ACTION_UP:
                            return true;

                        case MotionEvent.ACTION_MOVE:
                            params.x = initialx+(int)(motionEvent.getRawX() - initialTouchx);
                            params.y = initialy + (int)(motionEvent.getRawY() - initialTouchy);

                            mWindowManager.updateViewLayout(mFloatingWidget,params);

                    }

                    return true;

                }
            });


        }

        return super.onStartCommand(intent, flags, startId);
    }


    private void initializePlayer() {

        exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext());
        playerView.setPlayer(exoPlayer);

        MediaSource mediaSource = buildMediaSource(videoUri);

        exoPlayer.setPlayWhenReady(true);
        //  player.seekTo(currentWindow, playbackPosition);
        exoPlayer.prepare(mediaSource, false, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getContext(), "exoplayer-codelab");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    private void playVideo() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mFloatingWidget!=null)
        {
            mWindowManager.removeView(mFloatingWidget);
        }
    }
}
