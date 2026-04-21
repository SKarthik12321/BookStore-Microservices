package com.bookstore.adminservice.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@FeignClient(name="ORDER-SERVICE",path="/api/orders")
public interface OrderServiceClient {
    @GetMapping("/all") List<Map<String,Object>> getAllOrders(@RequestHeader("Authorization") String token);
    @PutMapping("/{id}/status") Map<String,Object> updateStatus(@PathVariable Long id,@RequestBody Map<String,Object> req,@RequestHeader("Authorization") String token);
}