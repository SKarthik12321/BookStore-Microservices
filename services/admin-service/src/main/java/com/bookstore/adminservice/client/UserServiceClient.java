package com.bookstore.adminservice.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@FeignClient(name="USER-SERVICE",path="/api/users")
public interface UserServiceClient {
    @GetMapping("/all") List<Map<String,Object>> getAllUsers(@RequestHeader("Authorization") String token);
}