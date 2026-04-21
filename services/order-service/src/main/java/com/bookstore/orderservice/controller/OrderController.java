package com.bookstore.orderservice.controller;
import com.bookstore.orderservice.dto.OrderDto;
import com.bookstore.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/orders") @Tag(name="Orders")
public class OrderController {
    @Autowired private OrderService orderService;
    @PostMapping @Operation(summary="Place new order from cart")
    public ResponseEntity<OrderDto.OrderResponse> place(Authentication auth, HttpServletRequest req,@Valid @RequestBody OrderDto.PlaceOrderRequest body){
        return ResponseEntity.status(201).body(orderService.placeOrder(auth.getName(),"Bearer "+req.getHeader("Authorization").substring(7),body));
    }
    @GetMapping @Operation(summary="Get my orders")
    public ResponseEntity<List<OrderDto.OrderResponse>> myOrders(Authentication auth){ return ResponseEntity.ok(orderService.getUserOrders(auth.getName())); }
    @GetMapping("/{id}") @Operation(summary="Get order by ID")
    public ResponseEntity<OrderDto.OrderResponse> getOrder(Authentication auth,@PathVariable Long id){ return ResponseEntity.ok(orderService.getOrder(id,auth.getName())); }
    @PutMapping("/{id}/cancel") @Operation(summary="Cancel pending order")
    public ResponseEntity<OrderDto.OrderResponse> cancel(Authentication auth,@PathVariable Long id){ return ResponseEntity.ok(orderService.cancelOrder(id,auth.getName())); }
    @GetMapping("/all") @PreAuthorize("hasRole('ADMIN')") @Operation(summary="All orders (admin)")
    public ResponseEntity<List<OrderDto.OrderResponse>> all(){ return ResponseEntity.ok(orderService.getAllOrders()); }
    @PutMapping("/{id}/status") @PreAuthorize("hasRole('ADMIN')") @Operation(summary="Update order status")
    public ResponseEntity<OrderDto.OrderResponse> updateStatus(@PathVariable Long id,@RequestBody OrderDto.UpdateStatusRequest req){ return ResponseEntity.ok(orderService.updateStatus(id,req)); }
}