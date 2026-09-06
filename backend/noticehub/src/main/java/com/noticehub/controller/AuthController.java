package com.noticehub.controller;

import com.noticehub.dto.LoginRequest;
import com.noticehub.dto.LoginResponse;
import com.noticehub.dto.RegisterRequest;
import com.noticehub.entity.User;
import com.noticehub.service.UserService;
import com.noticehub.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            userService.registerUser(
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                request.getFullName(),
                request.getRole()
            );
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            User user = userService.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
            
            if (!userService.validatePassword(request.getPassword(), user.getPassword())) {
                return ResponseEntity.badRequest().body("Invalid credentials");
            }
            
            String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
            
            return ResponseEntity.ok(new LoginResponse(
                token,
                user.getUsername(),
                user.getRole().name(),
                user.getFullName()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}