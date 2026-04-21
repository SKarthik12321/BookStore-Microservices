package com.bookstore.adminservice.controller;
import com.bookstore.adminservice.client.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController @RequestMapping("/api/admin") @Tag(name="Admin")
public class AdminController {
    @Autowired private ProductServiceClient productServiceClient;
    @Autowired private OrderServiceClient orderServiceClient;
    @Autowired private UserServiceClient userServiceClient;

    private String bearer(HttpServletRequest req){ return req.getHeader("Authorization"); }

    @GetMapping("/all-users") public ResponseEntity<List<Map<String,Object>>> allUsers(HttpServletRequest req){ return ResponseEntity.ok(userServiceClient.getAllUsers(bearer(req))); }
    @PutMapping("/products/{id}") public ResponseEntity<Map<String,Object>> updateProduct(HttpServletRequest req,@PathVariable Long id,@RequestBody Map<String,Object> body){ return ResponseEntity.ok(productServiceClient.updateProduct(id,body,bearer(req))); }
    @DeleteMapping("/products/{id}") public ResponseEntity<Void> deleteProduct(HttpServletRequest req,@PathVariable Long id){ productServiceClient.deleteProduct(id,bearer(req)); return ResponseEntity.noContent().build(); }
    @GetMapping("/orders") public ResponseEntity<List<Map<String,Object>>> orders(HttpServletRequest req){ return ResponseEntity.ok(orderServiceClient.getAllOrders(bearer(req))); }
    @PutMapping("/orders/{id}/status") public ResponseEntity<Map<String,Object>> updateStatus(HttpServletRequest req,@PathVariable Long id,@RequestBody Map<String,Object> body){ return ResponseEntity.ok(orderServiceClient.updateStatus(id,body,bearer(req))); }
}