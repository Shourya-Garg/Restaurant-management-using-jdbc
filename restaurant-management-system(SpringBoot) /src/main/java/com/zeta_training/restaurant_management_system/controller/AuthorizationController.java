package com.zeta_training.restaurant_management_system.controller;

import com.zeta_training.restaurant_management_system.dto.LoginDTO;
import com.zeta_training.restaurant_management_system.dto.RegisterDTO;
import com.zeta_training.restaurant_management_system.service.AuthorizationService;
import com.zeta_training.restaurant_management_system.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@Slf4j
public class AuthorizationController {
    private AuthorizationService authorizationService;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthorizationController(AuthorizationService authorizationService, JwtUtil jwtUtil) {
        this.authorizationService = authorizationService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody RegisterDTO request) {
        log.info("Admin user registering: {}", request.getFirstName());
        authorizationService.registerAdmin(request);
        return ResponseEntity.ok("Admin user registered successfully");
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO request) {
        log.info("User login attempt: {}", request.getEmail());
        String token = authorizationService.login(request);
        return ResponseEntity.ok(token);
    }
}
