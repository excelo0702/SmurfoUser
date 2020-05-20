package com.example.chhots.ChatBox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chhots.R;
import com.example.chhots.UserInfoModel;
import com.example.chhots.ui.Dashboard.ChatPeopleModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatWithInstructor extends AppCompatActivity {



    EditText message;
    ImageView send_message;
    private String instructor_id,routineId;
    private String userId;
    private String TAG = "ChatWithInstructor12345";
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private ImageView send_video;
    private List<MessageModel> list;
    private MessageAdapter adapter;

    String instructorImage,instructorName;
    String userImage,userName;

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_with_instructor);

        Intent intent = getIntent();
        instructor_id = intent.getStringExtra("instructorId");
        routineId = intent.getStringExtra("routineId");
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
        adapter = new MessageAdapter(ChatWithInstructor.this,list);


        fetchUserInfo();
        fetchInstructorInfo();

        showMessage();
        send_message.setOnClickListener(new View.OnClickListener() {
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

    private void fetchInstructorInfo()
    {
        databaseReference.child("UserInfo").child(instructor_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInfoModel model = dataSnapshot.getValue(UserInfoModel.class);
                instructorName = model.getUserName();
                instructorImage = model.getUserImageurl();
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
        MessageModel model = new MessageModel(mess,time,0,"");

            // user is sending a message
            model.setFlag(1);
            databaseReference.child("CHAT").child("Instructor").child(instructor_id).child(routineId).child(userId).child(time).setValue(model);
            model.setFlag(0);
            databaseReference.child("CHAT").child("Users").child(userId).child(routineId).child(time).setValue(model);


        ChatPeopleModel mode1 = new ChatPeopleModel(userId,userImage,userName);
        databaseReference.child("CHAT_LIST").child(instructor_id).child(userId).setValue(mode1);

        ChatPeopleModel mode2 = new ChatPeopleModel(instructor_id,instructorImage,instructorName);
        databaseReference.child("CHAT_LIST").child(userId).child(instructor_id).setValue(mode1);
        message.setText("");
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
