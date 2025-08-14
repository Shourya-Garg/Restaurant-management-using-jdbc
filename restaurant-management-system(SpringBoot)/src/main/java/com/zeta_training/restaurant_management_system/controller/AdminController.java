package com.zeta_training.restaurant_management_system.controller;

import com.zeta_training.restaurant_management_system.dto.BillResponseDTO;
import com.zeta_training.restaurant_management_system.dto.RegisterDTO;
import com.zeta_training.restaurant_management_system.service.AuthService;
import com.zeta_training.restaurant_management_system.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AuthService authService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/registerRestaurantEmployee")
    public ResponseEntity<String> registerRestaurantEmployee(@RequestBody RegisterDTO request) {

        authService.registerRestaurantEmployee(request);
        return ResponseEntity.ok("Restaurant employee registered successfully");
    }

    @PostMapping("/generateBill/{orderId}")
    public ResponseEntity<BillResponseDTO> generateBill(@PathVariable Long orderId) {
         return ResponseEntity.ok(orderService.generateBill(orderId));
    }
}
