package com.yashporwal.BillingSoftware.service;

import com.yashporwal.BillingSoftware.io.OrderRequest;
import com.yashporwal.BillingSoftware.io.OrderResponse;
import com.yashporwal.BillingSoftware.io.PaymentVerificationRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    List<OrderResponse> getLatestOrders();

    void deleteOrder(String orderId);

    OrderResponse verifyPayment(PaymentVerificationRequest request);

    Double sumSalesByDate(LocalDate date);

    Long countByOrderDate(LocalDate date);

    List<OrderResponse> findRecentOrders();
}
