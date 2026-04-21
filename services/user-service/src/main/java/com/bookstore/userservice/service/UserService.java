package com.bookstore.userservice.service;

import com.bookstore.userservice.dto.UserDto;
import com.bookstore.userservice.entity.Role;
import com.bookstore.userservice.entity.User;
import com.bookstore.userservice.exception.UserAlreadyExistsException;
import com.bookstore.userservice.repository.UserRepository;
import com.bookstore.userservice.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserDetailsService userDetailsService;
    @Autowired private KafkaTemplate<String, Object> kafkaTemplate;

    public UserDto.AuthResponse register(UserDto.RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered: " + request.getEmail());
        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        user = userRepository.save(user);

        try {
            kafkaTemplate.send("user-events", java.util.Map.of(
                    "type", "USER_REGISTERED",
                    "userId", user.getId(),
                    "email", user.getEmail(),
                    "name", user.getName()
            ));
        } catch (Exception e) {
            // Kafka not required for service to work
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtUtil.generateToken(userDetails, user.getId(), user.getRole().name());

        return UserDto.AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

    public UserDto.AuthResponse login(UserDto.LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtUtil.generateToken(userDetails, user.getId(), user.getRole().name());

        return UserDto.AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

    public UserDto.UserResponse getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return toResponse(user);
    }

    public UserDto.UserResponse updateProfile(String email, UserDto.UpdateProfileRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setName(request.getName());
        return toResponse(userRepository.save(user));
    }

    public void changePassword(String email, UserDto.ChangePasswordRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserDto.UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    private UserDto.UserResponse toResponse(User user) {
        return UserDto.UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
