package com.bookstore.userservice.dto;

import com.bookstore.userservice.entity.Role;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

public class UserDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RegisterRequest {
        @NotBlank(message = "Name is required")
        private String name;

        @Email(message = "Invalid email format")
        @NotBlank(message = "Email is required")
        private String email;

        @Size(min = 8, message = "Password must be at least 8 characters")
        @Pattern(regexp = ".*[A-Z].*", message = "Password must contain an uppercase letter")
        @NotBlank(message = "Password is required")
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        @NotBlank(message = "Email is required")
        private String email;

        @NotBlank(message = "Password is required")
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AuthResponse {
        private String token;
        private String tokenType = "Bearer";
        private Long userId;
        private String email;
        private String name;
        private Role role;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserResponse {
        private Long id;
        private String email;
        private String name;
        private Role role;
        private LocalDateTime createdAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateProfileRequest {
        @NotBlank(message = "Name is required")
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangePasswordRequest {
        @NotBlank
        private String currentPassword;

        @Size(min = 8, message = "Password must be at least 8 characters")
        @Pattern(regexp = ".*[A-Z].*", message = "Password must contain uppercase letter")
        @NotBlank
        private String newPassword;
    }
}
