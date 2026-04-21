package com.bookstore.orderservice.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@FeignClient(name="CART-SERVICE",path="/api/cart")
public interface CartServiceClient {
    @GetMapping
    Map<String,Object> getCart(@RequestHeader("Authorization") String token);
    @DeleteMapping("/clear")
    void clearCart(@RequestHeader("Authorization") String token);
}