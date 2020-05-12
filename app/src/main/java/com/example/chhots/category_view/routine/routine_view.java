package com.example.chhots.category_view.routine;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chhots.MainActivity;
import com.example.chhots.PaymentListener;
import com.example.chhots.R;
import com.example.chhots.See_Video;
import com.example.chhots.UserClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class routine_view extends Fragment implements PaymentListener {


    public routine_view() {
        // Required empty public constructor
    }

    private TextView title,description;
    private ImageView videoThumbnail,userImage;
    private Button buy_now;

    private static final String TAGR = "RazorPay";
    String videoId;

    private FirebaseUser user;
    private DatabaseReference mDatabaseReference;

    private PaymentListener listener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_routine_view, container, false);
        title = view.findViewById(R.id.routine_view_title);
        description = view.findViewById(R.id.routine_description);
        videoThumbnail = view.findViewById(R.id.routine_view_image);
        userImage = view.findViewById(R.id.routine_user_image);
        Bundle bundle = this.getArguments();
        videoId = bundle.getString("videoId");
        String thumbnail = bundle.getString("thumbnail");
         String instructorId = bundle.getString("instructorId");
//TODO: change variable name of userId to instructor Id
        Picasso.get().load(Uri.parse(thumbnail)).into(videoThumbnail);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        buy_now = view.findViewById(R.id.routine_buy_now);
        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment("Tanish","Tanish","0723237826","https://s3.amazonaws.com/rzp-mobile/images/rzp.png","4000");
            }
        });


        return view;
    }

    public void startPayment(String merchant,String desc,String order,String imageUrl,String amoun) {


        String merchantName = merchant;
        String description = desc;
        String orderId = order;
        String image = imageUrl;
        String amount = amoun;

        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();


        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.ic_launcher_background);

        /**
         * Reference to current activity
         */
        final Activity activity = getActivity();

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */
            options.put("name", merchantName);

            /**
             * Description can be anything
             * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.
             *     Invoice Payment
             *     etc.
             */
            options.put("description", desc);
            options.put("image", image);
            //    options.put("order_id", "order_9A33XWu170gUtm");
            options.put("currency", "INR");

            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            options.put("amount", amount);

            checkout.open(activity, options);
        } catch(Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            Log.e(TAGR, "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        try{
            String time = System.currentTimeMillis()+"";
            UserClass model = new UserClass(videoId,time);
            mDatabaseReference.child("USERS").child(user.getUid()).child("videos").child(videoId).setValue(model);


            Fragment fragment = new See_Video();
            Bundle bundle = new Bundle();
            bundle.putString("videoId", videoId);
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.drawer_layout, fragment);
            fragmentTransaction.commitAllowingStateLoss();



        }
        catch (Exception e)
        {
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

        }

    }
    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getContext(),"Nop Fragment",Toast.LENGTH_SHORT).show();
    }
}
