package com.zeta_training.restaurant_management_system.controller;

import com.zeta_training.restaurant_management_system.dto.OrderRequest;
import com.zeta_training.restaurant_management_system.dto.StatusDTO;
import com.zeta_training.restaurant_management_system.entity.Order;
import com.zeta_training.restaurant_management_system.enumeration.OrderItemStatus;
import com.zeta_training.restaurant_management_system.service.CustomerOrderService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CustomerOrderController {
    private CustomerOrderService customerOrderService;

    @Autowired
    public CustomerOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest request) {
        log.info("Customer Order request received: {}", request);
        Order savedOrder = customerOrderService.placeOrUpdateOrder(request);
        return ResponseEntity.ok(savedOrder);
    }

    @PutMapping("/updateOrderItem/{orderItemId}")
    public ResponseEntity<String> updateOrderItem(@PathVariable Long orderItemId, @RequestBody StatusDTO status) {
        log.info(" Customer order item updating with ID: {} to status: {}", orderItemId, status.getStatus());
        customerOrderService.updateOrderItemStatus(orderItemId, OrderItemStatus.valueOf(status.getStatus().toUpperCase()));
        return ResponseEntity.ok("Customer Order item status updated successfully");
    }
}

