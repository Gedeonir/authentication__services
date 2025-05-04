package com.africahr.auth_service.controller;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.africahr.auth_service.dto.LoginRequest;
import com.africahr.auth_service.dto.RegisterRequest;
import com.africahr.auth_service.model.Employee;
import com.africahr.auth_service.repository.UserRepository;
import com.africahr.auth_service.security.JwtUtil;
import com.africahr.auth_service.service.EmailService;
import com.africahr.auth_service.service.UserService;
import com.africahr.auth_service.util.PasswordUtil;

import java.util.Optional;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        
        String randomPassword = PasswordUtil.generateRandomPassword(10);
        
        Employee user = new Employee();
        user.setEmail(request.getEmail().trim());
        user.setPassword(request.getPassword());
        user.setName(request.getName());
        user.setRole(request.getRole());
        user.setDepartment(request.getDepartment());
        user.setAvatarUrl(request.getAvatarUrl());

        Employee registered = userService.registerUser(user);

        emailService.sendEmail(
            user.getEmail(),
            "Welcome to the Platform",
            "Your account has been created.\n\nEmail: " + user.getEmail() + "\nPassword: " + randomPassword
        );

        return ResponseEntity.ok("User registered successfully and password sent via email.");
    }


    @GetMapping("/employees")
    public ResponseEntity<?> getAllUsers() {
        List<Employee> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody Employee updated) {
        Employee result = userService.updateUser(id, updated);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        Employee user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Employee> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Employee user = optionalUser.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", user.getRole());
        response.put("id", user.getId());

        return ResponseEntity.ok(response);
    }
}
