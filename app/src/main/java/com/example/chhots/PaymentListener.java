package com.example.chhots;

import android.util.Log;
import android.widget.Toast;

import com.razorpay.PaymentResultListener;

public interface PaymentListener extends PaymentResultListener {
    public void onPaymentSuccess(String s);
    public void onPaymentError(int i, String s);
}
