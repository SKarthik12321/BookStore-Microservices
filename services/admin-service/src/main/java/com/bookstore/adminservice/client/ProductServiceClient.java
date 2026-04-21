package com.bookstore.adminservice.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@FeignClient(name="PRODUCT-SERVICE",path="/api")
public interface ProductServiceClient {
    @PutMapping("/products/{id}") Map<String,Object> updateProduct(@PathVariable Long id,@RequestBody Map<String,Object> req,@RequestHeader("Authorization") String token);
    @DeleteMapping("/products/{id}") void deleteProduct(@PathVariable Long id,@RequestHeader("Authorization") String token);
    @GetMapping("/products") Map<String,Object> getAllProducts(@RequestHeader("Authorization") String token);
}