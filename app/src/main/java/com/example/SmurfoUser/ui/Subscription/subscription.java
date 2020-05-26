package com.example.SmurfoUser.ui.Subscription;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.SmurfoUser.R;
import com.example.SmurfoUser.onBackPressed;
import com.example.SmurfoUser.ui.home.HomeFragment;
import com.razorpay.Checkout;

import org.json.JSONObject;

public class subscription extends Fragment implements onBackPressed {


    private TextView routine_1,routine_2,routine_3,routine_4;
    private TextView course_1,course_2,course_3,course_4;
    private TextView full_1,full_2,full_3,full_4;
    private TextView no_1,no_2,no_3;
    private static final String TAG = "RazorPay";



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_subscription, container, false);
        init(root);

        Checkout.preload(getContext());
        routine_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment("Tanish","Tanish","0723237826","https://s3.amazonaws.com/rzp-mobile/images/rzp.png","4000");
            }
        });
        routine_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment("Tanish","Tanish","0723237826","https://s3.amazonaws.com/rzp-mobile/images/rzp.png","4000");
            }
        });
        routine_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment("Tanish","Tanish","0723237826","https://s3.amazonaws.com/rzp-mobile/images/rzp.png","4000");

            }
        });
        routine_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment("Tanish","Tanish","0723237826","https://s3.amazonaws.com/rzp-mobile/images/rzp.png","4000");

            }
        });

        course_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment("Tanish","Tanish","0723237826","https://s3.amazonaws.com/rzp-mobile/images/rzp.png","4000");
            }
        });
        course_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment("Tanish","Tanish","0723237826","https://s3.amazonaws.com/rzp-mobile/images/rzp.png","4000");
            }
        });
        course_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment("Tanish","Tanish","0723237826","https://s3.amazonaws.com/rzp-mobile/images/rzp.png","4000");

            }
        });
        course_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment("Tanish","Tanish","0723237826","https://s3.amazonaws.com/rzp-mobile/images/rzp.png","4000");

            }
        });

        full_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment("Tanish","Tanish","0723237826","https://s3.amazonaws.com/rzp-mobile/images/rzp.png","4000");
            }
        });
        full_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment("Tanish","Tanish","0723237826","https://s3.amazonaws.com/rzp-mobile/images/rzp.png","4000");
            }
        });
        full_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment("Tanish","Tanish","0723237826","https://s3.amazonaws.com/rzp-mobile/images/rzp.png","4000");

            }
        });
        full_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment("Tanish","Tanish","0723237826","https://s3.amazonaws.com/rzp-mobile/images/rzp.png","4000");

            }
        });

        no_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment("Tanish","Tanish","0723237826","https://s3.amazonaws.com/rzp-mobile/images/rzp.png","4000");
            }
        });
        no_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment("Tanish","Tanish","0723237826","https://s3.amazonaws.com/rzp-mobile/images/rzp.png","4000");
            }
        });
        no_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment("Tanish","Tanish","0723237826","https://s3.amazonaws.com/rzp-mobile/images/rzp.png","4000");

            }
        });



        return root;
    }

    private void init(View view) {
        routine_1 = view.findViewById(R.id.routine_1);
        routine_2 = view.findViewById(R.id.routine_2);
        routine_3 = view.findViewById(R.id.routine_3);
        routine_4 = view.findViewById(R.id.routine_4);
        course_1 = view.findViewById(R.id.course_1);
        course_2 = view.findViewById(R.id.course_2);
        course_3 = view.findViewById(R.id.course_3);
        course_4 = view.findViewById(R.id.course_4);
        full_1 = view.findViewById(R.id.full_1);
        full_2 = view.findViewById(R.id.full_2);
        full_3 = view.findViewById(R.id.full_3);
        full_4 = view.findViewById(R.id.full_4);
        no_1 = view.findViewById(R.id.no_1);
        no_2 = view.findViewById(R.id.no_2);
        no_3 = view.findViewById(R.id.no_3);
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
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }


    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.drawer_layout,fragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        setFragment(new HomeFragment());
    }



}