package com.zeta_training.restaurant_management_system.controller;

import com.zeta_training.restaurant_management_system.dto.BillResponseDTO;
import com.zeta_training.restaurant_management_system.dto.RegisterDTO;
import com.zeta_training.restaurant_management_system.service.AuthorizationService;
import com.zeta_training.restaurant_management_system.service.CustomerOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    private AuthorizationService authorizationService;
    private CustomerOrderService customerOrderService;

    @Autowired
    public AdminController(AuthorizationService authorizationService, CustomerOrderService customerOrderService) {
        this.authorizationService = authorizationService;
        this.customerOrderService = customerOrderService;
    }

    @PostMapping("/registerRestaurantEmployee")
    public ResponseEntity<String> registerEmployee(@RequestBody RegisterDTO request) {

        log.info("Employee Registered: {}", request.getEmail());
        authorizationService.registerEmployee(request);
        return ResponseEntity.ok("Restaurant employee registered successfully");
    }

    @PostMapping("/generateBill/{orderId}")
    public ResponseEntity<BillResponseDTO> billGenerate(@PathVariable Long orderId) {
         log.info("Bill Generated for order ID: {}", orderId);
         return ResponseEntity.ok(customerOrderService.billGenerate(orderId));
    }
}
