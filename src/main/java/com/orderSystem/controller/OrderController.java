package com.orderSystem.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderSystem.dto.ApiResponse;
import com.orderSystem.dto.OrderRequest;
import com.orderSystem.entity.Order;
import com.orderSystem.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;
    
    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ApiResponse<Order> placeOrder(@Valid @RequestBody OrderRequest request) {
    	 return ApiResponse.success(
    	            "Order placed",
    	            service.placeOrder(request)
    	    );
    }

    @GetMapping("/user/{id}")
    public List<Order> getOrders(@PathVariable Long id) {
        return service.getUserOrders(id);
    }
}
