package com.example.SmurfoUser.category_view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.SmurfoUser.Notifications.APIService;
import com.example.SmurfoUser.Notifications.Client;
import com.example.SmurfoUser.Notifications.Data;
import com.example.SmurfoUser.Notifications.MyResponse;
import com.example.SmurfoUser.Notifications.Sender;
import com.example.SmurfoUser.Notifications.Token;
import com.example.SmurfoUser.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class live extends Fragment {


    public live() {
        // Required empty public constructor
    }


    EditText UserTB,Title,Message;
    Button send;
    private APIService apiService;
    String p;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.fragment_live, container, false);

        UserTB=view.findViewById(R.id.UserID);
        Title=view.findViewById(R.id.Title);
        Message=view.findViewById(R.id.Message);
        send=view.findViewById(R.id.button);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        p="Z6MnQXZuAWQKrFqCUuHw696XDHy1";


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String token = instanceIdResult.getToken();
                        updateToken(token);
                    }
                });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Tokens").child(p)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.d("fuckoff","FUCKER");

                                    Token model = dataSnapshot.getValue(Token.class);
                                    Log.d("fuckoff",model.getToken());
                                    Toast.makeText(getContext(), model.getToken(), Toast.LENGTH_SHORT).show();

                                    sendNotifications(model.getToken(), Title.getText().toString().trim(),Message.getText().toString().trim());

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getContext(), "Failed 15 ", Toast.LENGTH_LONG).show();

                            }
                        });
            }
        });
        return view;
    }

    private void updateToken(String token)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token1);
    }
    public void sendNotifications(String usertoken, String title, String message) {
        Data data = new Data(title, message);
        Sender sender = new Sender(data, usertoken);
  //      Toast.makeText(getContext(), "Failed 7 ", Toast.LENGTH_LONG).show();

        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {

            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
            //    Toast.makeText(getContext(), "Failed 9 ", Toast.LENGTH_LONG).show();
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(getContext(), "Failed ", Toast.LENGTH_LONG).show();
                    }
                    else
                    {

                        Toast.makeText(getContext(), "Success ", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }

}