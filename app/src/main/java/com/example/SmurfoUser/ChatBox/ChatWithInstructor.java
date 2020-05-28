package com.example.SmurfoUser.ChatBox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.SmurfoUser.R;
import com.example.SmurfoUser.UserInfoModel;
import com.example.SmurfoUser.ui.Dashboard.ApproveVideo.ChatPeopleModel;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

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

public class ChatWithInstructor extends AppCompatActivity {



    EditText message;
    ImageView send_message,instructorImage;
    TextView instructorName;
    private String instructor_id,routineId,category,peopleId;
    private String userId;
    private String TAG = "ChatWithInstructor12345";
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private ImageView send_video;
    private List<MessageModel> list;
    private MessageAdapter adapter;

    String userImage,userName,instructorImageurl,localtime;

    FirebaseAuth auth;
    FirebaseUser user;


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


    private void sendNotification() {
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


            mainObj.put("notification",notificationObj);
            mainObj.put("data",extraData);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    mainObj, new com.android.volley.Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(getApplicationContext(),"sent",Toast.LENGTH_SHORT).show();
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
                UserInfoModel model = dataSnapshot.getValue(UserInfoModel.class);
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
            databaseReference.child("CHAT_LIST").child(instructor_id).child(userId).setValue(mode1);

            ChatPeopleModel mode2 = new ChatPeopleModel(instructor_id, instructorImageurl, instructorName.getText().toString());
            databaseReference.child("CHAT_LIST").child(userId).child(instructor_id).setValue(mode1);
            message.setText("");

            sendNotification();



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
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

    }


}
