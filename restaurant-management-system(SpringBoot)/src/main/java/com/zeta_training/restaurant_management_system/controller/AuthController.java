package com.zeta_training.restaurant_management_system.controller;

import com.zeta_training.restaurant_management_system.dto.LoginDTO;
import com.zeta_training.restaurant_management_system.dto.RegisterDTO;
import com.zeta_training.restaurant_management_system.service.AuthService;
import com.zeta_training.restaurant_management_system.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/registerAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody RegisterDTO request) {
        authService.registerAdmin(request);
        return ResponseEntity.ok("Admin registered successfully");
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO request) {
        String token = authService.login(request);
        return ResponseEntity.ok(token);
    }
}
