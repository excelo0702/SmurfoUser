package com.example.SmurfoUser.Notifications;

import com.example.SmurfoUser.Notifications.MyResponse;
import com.example.SmurfoUser.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAk4aOdMQ:APA91bGIGSc4-YEFm1lkWu5fiP9Cg8NRT0hC4Jwkg4yhn2GkGQH4uD-FNoDMkDW8Hl_pULwRfj7EFMLW--qnTIH6WUNG7_ZkH9_6Z-Mo6ATU30SErTZJNYe7K69AsljvTxhVavn1XW56"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
