package com.bookstore.cartservice.controller;
import com.bookstore.cartservice.dto.CartDto;
import com.bookstore.cartservice.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
@RestController @RequestMapping("/api/cart") @Tag(name="Cart")
public class CartController {
    @Autowired private CartService cartService;
    @GetMapping @Operation(summary="Get user cart")
    public ResponseEntity<CartDto.CartResponse> getCart(Authentication auth){ return ResponseEntity.ok(cartService.getCart(auth.getName())); }
    @PostMapping("/add") @Operation(summary="Add item to cart")
    public ResponseEntity<CartDto.CartResponse> add(Authentication auth,@Valid @RequestBody CartDto.AddItemRequest req){ return ResponseEntity.ok(cartService.addItem(auth.getName(),req)); }
    @PutMapping("/update") @Operation(summary="Update item quantity")
    public ResponseEntity<CartDto.CartResponse> update(Authentication auth,@Valid @RequestBody CartDto.UpdateItemRequest req){ return ResponseEntity.ok(cartService.updateItem(auth.getName(),req)); }
    @DeleteMapping("/remove/{productId}") @Operation(summary="Remove item from cart")
    public ResponseEntity<CartDto.CartResponse> remove(Authentication auth,@PathVariable Long productId){ return ResponseEntity.ok(cartService.removeItem(auth.getName(),productId)); }
    @DeleteMapping("/clear") @Operation(summary="Clear cart")
    public ResponseEntity<Void> clear(Authentication auth){ cartService.clearCart(auth.getName()); return ResponseEntity.noContent().build(); }
    @GetMapping("/total") @Operation(summary="Get cart total")
    public ResponseEntity<BigDecimal> total(Authentication auth){ return ResponseEntity.ok(cartService.getTotal(auth.getName())); }
}