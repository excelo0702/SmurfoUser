package com.example.SmurfoUser;

import com.razorpay.PaymentResultListener;

public interface PaymentListener extends PaymentResultListener {
    public void onPaymentSuccess(String s);
    public void onPaymentError(int i, String s);
}
