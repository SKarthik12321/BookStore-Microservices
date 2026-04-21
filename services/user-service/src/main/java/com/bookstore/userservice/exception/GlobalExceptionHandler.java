package com.bookstore.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, Object> error(int status, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("message", message);
        map.put("timestamp", LocalDateTime.now().toString());
        return map;
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleExists(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error(409, ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error(401, "Invalid email or password"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(error(400, msg));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegal(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(error(400, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error(500, "Internal server error"));
    }
}
