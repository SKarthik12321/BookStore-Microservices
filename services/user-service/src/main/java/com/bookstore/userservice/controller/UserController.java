package com.bookstore.userservice.controller;

import com.bookstore.userservice.dto.UserDto;
import com.bookstore.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User management APIs")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    public ResponseEntity<UserDto.AuthResponse> register(@Valid @RequestBody UserDto.RegisterRequest request) {
        return ResponseEntity.status(201).body(userService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Login and get JWT token")
    public ResponseEntity<UserDto.AuthResponse> login(@Valid @RequestBody UserDto.LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/profile")
    @Operation(summary = "Get authenticated user profile")
    public ResponseEntity<UserDto.UserResponse> getProfile(Authentication authentication) {
        return ResponseEntity.ok(userService.getProfile(authentication.getName()));
    }

    @PutMapping("/profile")
    @Operation(summary = "Update user profile")
    public ResponseEntity<UserDto.UserResponse> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UserDto.UpdateProfileRequest request) {
        return ResponseEntity.ok(userService.updateProfile(authentication.getName(), request));
    }

    @PutMapping("/change-password")
    @Operation(summary = "Change password")
    public ResponseEntity<String> changePassword(
            Authentication authentication,
            @Valid @RequestBody UserDto.ChangePasswordRequest request) {
        userService.changePassword(authentication.getName(), request);
        return ResponseEntity.ok("Password changed successfully");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user (Admin only)")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all users (Admin only)")
    public ResponseEntity<List<UserDto.UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
