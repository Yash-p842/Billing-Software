package com.yashporwal.BillingSoftware.service;

import com.razorpay.RazorpayException;
import com.yashporwal.BillingSoftware.io.RazorpayOrderResponse;

public interface RazorpayService {

    RazorpayOrderResponse createOrder(Double amount, String currency) throws RazorpayException;
}
