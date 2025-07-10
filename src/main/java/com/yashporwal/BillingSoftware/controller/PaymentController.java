package com.yashporwal.BillingSoftware.controller;

import com.razorpay.RazorpayException;
import com.yashporwal.BillingSoftware.io.OrderResponse;
import com.yashporwal.BillingSoftware.io.PaymentRequest;
import com.yashporwal.BillingSoftware.io.PaymentVerificationRequest;
import com.yashporwal.BillingSoftware.io.RazorpayOrderResponse;
import com.yashporwal.BillingSoftware.service.OrderService;
import com.yashporwal.BillingSoftware.service.RazorpayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private RazorpayService razorpayService;
    @Autowired
    private OrderService orderService;

    @PostMapping("create-order")
    @ResponseStatus(HttpStatus.CREATED)
    public RazorpayOrderResponse createRazorpayOrder(@RequestBody PaymentRequest request) throws RazorpayException{
        return razorpayService.createOrder(request.getAmount(), request.getCurrency());
    }

    @PostMapping("/verify")
    public OrderResponse verifyPayment(@RequestBody PaymentVerificationRequest request){
        return orderService.verifyPayment(request);
    }
}
