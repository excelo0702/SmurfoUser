package com.example.SmurfoUser.ChatBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.SmurfoUser.NotificationNumberModel;
import com.example.SmurfoUser.R;
import com.example.SmurfoUser.UserInfoModel;
import com.example.SmurfoUser.bottom_navigation_fragments.InstructorPackage.InstructorInfoModel;
import com.example.SmurfoUser.ui.Dashboard.ApproveVideo.ChatPeopleModel;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;

import static android.view.View.GONE;

public class ChatWithInstructor extends AppCompatActivity {



    EditText message;
    ImageView send_message,instructorImage,send_video;
    TextView instructorName,text_video;
    private String instructor_id,routineId,category,peopleId;
    private String userId;
    private String TAG = "ChatWithInstructor12345";
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private List<MessageModel> list;
    private MessageAdapter adapter;
    NotificationNumberModel model;
    String userImage,userName,instructorImageurl,localtime;
    int flag=0;
    FirebaseAuth auth;
    FirebaseUser user;
    ProgressBar progressBar;
    PopupWindow mPopupWindow;

    RelativeLayout tt;
    PlayerView playerView;
    SimpleExoPlayer player;
    boolean playWhenReady = true;
    int currentWindow = 0;
    long playbackPosition = 0;
    ImageView fullScreenButton;
    boolean fullScreen = false;
    Switch sw1;

    private Uri videouri;
    private RelativeLayout relativeLayout;

    //Notification

    private RequestQueue mRequestQueue;
    private String URL="https://fcm.googleapis.com/fcm/send";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_with_instructor);

        Intent intent = getIntent();
        category = intent.getStringExtra("category");

        send_message = findViewById(R.id.send_message);
        message = findViewById(R.id.message);
        databaseReference = FirebaseDatabase.getInstance().getReference("");
        recyclerView = findViewById(R.id.recycler_chat_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userId = user.getUid();
        send_video = findViewById(R.id.send_video_chat);
        progressBar = findViewById(R.id.progress_bar_chat_video);
        text_video = findViewById(R.id.text_video);
        relativeLayout = findViewById(R.id.chat_screen);

        adapter = new MessageAdapter(ChatWithInstructor.this, list, new MessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MessageModel model) {
                Toast.makeText(getApplicationContext(),model.getTime(),Toast.LENGTH_SHORT).show();
            }
        });
        instructorImage = findViewById(R.id.instructor_profile_image);
        instructorName = findViewById(R.id.instructor_profile_name);


            instructor_id = intent.getStringExtra("instructorId");
            routineId = intent.getStringExtra("routineId");
            fetchUserInfo();
            fetchInstructorInfo();


        mRequestQueue = Volley.newRequestQueue(this);
        FirebaseMessaging.getInstance().subscribeToTopic(userId);


        showMessage();
        send_message.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
        send_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVideo();
            }
        });


    }

    private void sendNotification(String message) {
  /*      //json object
        {
            "to": "topics/topic name"
                notification:   {
                    title: "some titlle"
                     body:  "some body"
                }
        }*/

        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("to","/topics/"+instructor_id);
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title",userName);
            notificationObj.put("body",message);

            JSONObject extraData = new JSONObject();
            extraData.put("category","Chat");
            extraData.put("peopleId",userId);
            extraData.put("routineId",routineId);


            mainObj.put("notification",notificationObj);
            mainObj.put("data",extraData);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    mainObj, new com.android.volley.Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> header = new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAAk4aOdMQ:APA91bGIGSc4-YEFm1lkWu5fiP9Cg8NRT0hC4Jwkg4yhn2GkGQH4uD-FNoDMkDW8Hl_pULwRfj7EFMLW--qnTIH6WUNG7_ZkH9_6Z-Mo6ATU30SErTZJNYe7K69AsljvTxhVavn1XW56");
                    return header;
                }
            };

            mRequestQueue.add(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void fetchInstructorInfo() {

        databaseReference.child("InstructorInfo").child(instructor_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                InstructorInfoModel model = dataSnapshot.getValue(InstructorInfoModel.class);
                instructorName.setText(model.getUserName());
                Picasso.get().load(Uri.parse(model.getUserImageurl())).into(instructorImage);
                instructorImageurl = model.getUserImageurl();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void fetchUserInfo()
    {
        databaseReference.child("UserInfo").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInfoModel model = dataSnapshot.getValue(UserInfoModel.class);
                userName = model.getUserName();
                userImage = model.getUserImageurl();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendVideo() {
        chooseVideo();
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("2323232","111111");
        if(requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!=null)
        {
            videouri = data.getData();
            showVideoUtil();

        }
    }

    private void sendVideoFull() {
        final String time = System.currentTimeMillis()+"";
        progressBar.setVisibility(View.VISIBLE);
        text_video.setVisibility(View.VISIBLE);
        final StorageReference reference = FirebaseStorage.getInstance().getReference("ChatVideos").child(userId).child(routineId).child(time+getfilterExt(videouri));
        reference.putFile(videouri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                send_video.setEnabled(true);
                                MessageModel model = new MessageModel(uri.toString(),localtime,0,"video");
                                model.setFlag(1);
                                databaseReference.child("CHAT").child("Instructor").child(instructor_id).child(routineId).child(userId).child(time).setValue(model);
                                model.setFlag(0);
                                databaseReference.child("CHAT").child("Users").child(userId).child(routineId).child(time).setValue(model);

                                playerView.setVisibility(GONE);


                                ChatPeopleModel mode1 = new ChatPeopleModel(userId, userImage, userName);
                                databaseReference.child("CHAT_LIST").child(instructor_id).child(routineId).child(userId).setValue(mode1);

                                ChatPeopleModel mode2 = new ChatPeopleModel(instructor_id, instructorImageurl, instructorName.getText().toString());
                                databaseReference.child("CHAT_LIST").child(userId).child(routineId).child(instructor_id).setValue(mode1);
                                message.setText("");
                                recyclerView.scrollToPosition(list.size()-1);
                                text_video.setVisibility(GONE);
                                progressBar.setVisibility(GONE);
                                sendNotification("Video");
                                setNotificationNumber();
                            }
                        });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0* taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        progressBar.setProgress((int) progress);
                    }
                });
        send_video.setEnabled(true);

    }

    private void showVideoUtil() {
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.exo_player_popup_window,null);


        mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        if(Build.VERSION.SDK_INT>=21){
            mPopupWindow.setElevation(5.0f);
        }
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAtLocation(relativeLayout, Gravity.CENTER,0,0);
        mPopupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    mPopupWindow.dismiss();
                }
                return false;
            }
        });
        ImageView send = customView.findViewById(R.id.send_video);
        ImageView close = customView.findViewById(R.id.close_video_screen);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });


        playerView = customView.findViewById(R.id.full_video_popup);
        fullScreenButton = playerView.findViewById(R.id.exo_fullscreen_icon);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        playerView.setPadding(5,0,5,0);
        sw1 = playerView.findViewById(R.id.mirror);
        sw1.setVisibility(GONE);
        fullScreenButton.setVisibility(GONE);
        initializePlayer();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_video.setEnabled(false);
                sendVideoFull();
                mPopupWindow.dismiss();
            }
        });


    }


    private String getfilterExt(Uri videoUri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(videoUri));
    }


    private void chooseVideo(){
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    public void sendMessage()
    {
        String mess = message.getText().toString();
        String time = System.currentTimeMillis()+"";

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm a");
        date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));

        localtime = date.format(currentLocalTime);
        MessageModel model = new MessageModel(mess,localtime,0,"");


            // user is sending a message
            model.setFlag(1);
            databaseReference.child("CHAT").child("Instructor").child(instructor_id).child(routineId).child(userId).child(time).setValue(model);
            model.setFlag(0);
            databaseReference.child("CHAT").child("Users").child(userId).child(routineId).child(time).setValue(model);




            ChatPeopleModel mode1 = new ChatPeopleModel(userId, userImage, userName);
            databaseReference.child("CHAT_LIST").child(routineId).child(instructor_id).child(userId).setValue(mode1);

            ChatPeopleModel mode2 = new ChatPeopleModel(instructor_id, instructorImageurl, instructorName.getText().toString());
            databaseReference.child("CHAT_LIST").child(userId).child(routineId).child(instructor_id).setValue(mode1);
            message.setText("");
            recyclerView.scrollToPosition(list.size()-1);
            sendNotification(mess);
            setNotificationNumber();
    }


    private void setNotificationNumber() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("NotificationNumber").child(instructor_id);
        final DatabaseReference query = db.child("dashboard").child("ApproveVideo").child("Routine").child(routineId).child(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.getChildrenCount()>0) {
                            model = dataSnapshot.getValue(NotificationNumberModel.class);
                            flag = 1;
                            model.setI(model.getI() + 1);
                            query.setValue(model);
                        }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        if(flag==0)
        {
            model = new NotificationNumberModel(userId,1);
            query.child(userId).setValue(model);

        }


        //get value of Routine

        //get value of approve video

        //get value of dashboard

    }

    public void showMessage()
    {
        Log.d(TAG,"show");

            databaseReference.child("CHAT").child("Users").child(userId).child(routineId)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            list.clear();
                            for(DataSnapshot ds: dataSnapshot.getChildren())
                            {
                                Log.d(TAG,ds.getValue().toString());
                                MessageModel model = ds.getValue(MessageModel.class);
                                list.add(model);
                            }
                            adapter.setData(list);
                            recyclerView.setAdapter(adapter);
                            recyclerView.smoothScrollToPosition(list.size());

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        databaseReference.child("CHAT").child("Users").child(userId).child(routineId).keepSynced(true);
    }


    private void initializePlayer() {

        player = ExoPlayerFactory.newSimpleInstance(getApplicationContext());
        playerView.setPlayer(player);
        MediaSource mediaSource = buildMediaSource(videouri);
        player.setPlayWhenReady(playWhenReady);
        player.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT);
        //  player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource, false, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getApplicationContext(), "exoplayer-codelab");
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
            if(player!=null){
                player.setPlayWhenReady(false);
            }        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23) {
            if(player!=null){
                player.setPlayWhenReady(false);
            }
            //   releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT <= 23) {

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

}
