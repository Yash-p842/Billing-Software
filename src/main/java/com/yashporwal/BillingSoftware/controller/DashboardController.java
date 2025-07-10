package com.yashporwal.BillingSoftware.controller;

import com.yashporwal.BillingSoftware.io.DashboardResponse;
import com.yashporwal.BillingSoftware.io.OrderResponse;
import com.yashporwal.BillingSoftware.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public DashboardResponse getDashboardData(){
        LocalDate today = LocalDate.now();
        Double todaySale = orderService.sumSalesByDate(today);
        Long todayOrderCount = orderService.countByOrderDate(today);
        List<OrderResponse> recentOrders = orderService.getLatestOrders();
        return new DashboardResponse(
                 todaySale != null ? todaySale : 0.0,
                todayOrderCount != null ? todayOrderCount : 0,
                recentOrders
        );
    }
}
