package com.yashporwal.BillingSoftware.controller;

import com.yashporwal.BillingSoftware.io.OrderRequest;
import com.yashporwal.BillingSoftware.io.OrderResponse;
import com.yashporwal.BillingSoftware.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse placeOrder(@RequestBody OrderRequest request){
        try {
            return orderService.createOrder(request);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error Occurred while placing Order"+e.getMessage());
        }
    }

    @GetMapping
    public List<OrderResponse> fetchAllOrders(){
        return orderService.getLatestOrders();
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeOrder(@PathVariable("orderId") String orderId){
        orderService.deleteOrder(orderId);
    }
}
