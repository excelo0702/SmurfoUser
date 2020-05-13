package com.example.chhots.category_view.Courses;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chhots.PaymentListener;
import com.example.chhots.R;
import com.example.chhots.See_Video;
import com.example.chhots.UserClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class course_purchase_view extends Fragment implements PaymentListener{


    public course_purchase_view() {
        // Required empty public constructor
    }


    private TextView title,description;
    private ImageView courseThumbnail,userImage;
    private Button buy_now;

    private static final String TAGR = "RazorPay";
    String courseId,instructorId,thumbnail;

    private FirebaseUser user;
    private DatabaseReference mDatabaseReference;

    private PaymentListener listener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_course_purchase_view, container, false);
        title = view.findViewById(R.id.course_view_title);
        description = view.findViewById(R.id.course_description);
        courseThumbnail = view.findViewById(R.id.course_view_image);
        userImage = view.findViewById(R.id.course_user_image);
        buy_now = view.findViewById(R.id.course_buy_now);
        Bundle bundle = this.getArguments();

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("");



        courseId = bundle.getString("courseId");
        thumbnail = bundle.getString("thumbnail");
        instructorId = bundle.getString("instructorId");


        Log.d(TAGR,thumbnail+"  yy  ");

//       Picasso.get().load(Uri.parse(thumbnail)).into(courseThumbnail);


        //fetch all the info of instructor


        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment("Tanish","Tanish","0723237826",thumbnail,"4000");
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
            UserClass model = new UserClass(time,courseId,0);
            mDatabaseReference.child("USERS").child(user.getUid()).child("courses").child(courseId).setValue(model);

            Fragment fragment = new course_view();
            Bundle bundle = new Bundle();
            bundle.putString("courseId", courseId);
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
