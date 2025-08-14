package com.zeta_training.restaurant_management_system.controller;

import com.zeta_training.restaurant_management_system.dto.OrderRequest;
import com.zeta_training.restaurant_management_system.dto.StatusDTO;
import com.zeta_training.restaurant_management_system.entity.Order;
import com.zeta_training.restaurant_management_system.enumeration.OrderItemStatus;
import com.zeta_training.restaurant_management_system.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private  OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest request) {
        Order savedOrder = orderService.placeOrUpdateOrder(request);
        return ResponseEntity.ok(savedOrder);
    }

    @PutMapping("/updateOrderItem/{orderItemId}")
    public ResponseEntity<String> updateOrderItem(@PathVariable Long orderItemId, @RequestBody StatusDTO status) {
        orderService.updateOrderItemStatus(orderItemId, OrderItemStatus.valueOf(status.getStatus().toUpperCase()));
        return ResponseEntity.ok("Order item status updated successfully");
    }
}

