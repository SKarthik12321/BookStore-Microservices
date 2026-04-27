package com.bookstore.notificationservice.controller;

import com.bookstore.notificationservice.dto.OrderEventRequest;
import com.bookstore.notificationservice.dto.UserEventRequest;
import com.bookstore.notificationservice.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notification Service", description = "Test notification endpoints — trigger emails manually without Kafka")
public class NotificationController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Check if notification service is running")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "notification-service");
        response.put("port", "8089");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/test/order-placed")
    @Operation(
        summary = "Test ORDER_PLACED notification",
        description = "Manually trigger an order confirmation email. Use this to test without Kafka."
    )
    public ResponseEntity<Map<String, String>> testOrderPlaced(@RequestBody OrderEventRequest request) {
        Map<String, Object> event = new HashMap<>();
        event.put("orderId", request.getOrderId());
        event.put("email", request.getEmail());
        event.put("type", "ORDER_PLACED");

        emailService.sendOrderConfirmation(event);

        Map<String, String> response = new HashMap<>();
        response.put("status", "sent");
        response.put("type", "ORDER_PLACED");
        response.put("orderId", String.valueOf(request.getOrderId()));
        response.put("to", request.getEmail());
        response.put("message", "Order confirmation notification triggered successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/test/order-shipped")
    @Operation(
        summary = "Test ORDER_SHIPPED notification",
        description = "Manually trigger a shipping update email."
    )
    public ResponseEntity<Map<String, String>> testOrderShipped(@RequestBody OrderEventRequest request) {
        Map<String, Object> event = new HashMap<>();
        event.put("orderId", request.getOrderId());
        event.put("email", request.getEmail());
        event.put("type", "ORDER_SHIPPED");

        emailService.sendShippingUpdate(event);

        Map<String, String> response = new HashMap<>();
        response.put("status", "sent");
        response.put("type", "ORDER_SHIPPED");
        response.put("orderId", String.valueOf(request.getOrderId()));
        response.put("to", request.getEmail());
        response.put("message", "Shipping update notification triggered successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/test/order-delivered")
    @Operation(
        summary = "Test ORDER_DELIVERED notification",
        description = "Manually trigger a delivery confirmation email."
    )
    public ResponseEntity<Map<String, String>> testOrderDelivered(@RequestBody OrderEventRequest request) {
        Map<String, Object> event = new HashMap<>();
        event.put("orderId", request.getOrderId());
        event.put("email", request.getEmail());
        event.put("type", "ORDER_DELIVERED");

        emailService.sendDeliveryConfirmation(event);

        Map<String, String> response = new HashMap<>();
        response.put("status", "sent");
        response.put("type", "ORDER_DELIVERED");
        response.put("orderId", String.valueOf(request.getOrderId()));
        response.put("to", request.getEmail());
        response.put("message", "Delivery confirmation notification triggered successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/test/welcome-email")
    @Operation(
        summary = "Test USER_REGISTERED welcome email",
        description = "Manually trigger a welcome email. Use this to test without Kafka."
    )
    public ResponseEntity<Map<String, String>> testWelcomeEmail(@RequestBody UserEventRequest request) {
        Map<String, Object> event = new HashMap<>();
        event.put("email", request.getEmail());
        event.put("name", request.getName());
        event.put("type", "USER_REGISTERED");

        emailService.sendWelcomeEmail(event);

        Map<String, String> response = new HashMap<>();
        response.put("status", "sent");
        response.put("type", "USER_REGISTERED");
        response.put("to", request.getEmail());
        response.put("message", "Welcome email notification triggered successfully");
        return ResponseEntity.ok(response);
    }
}
