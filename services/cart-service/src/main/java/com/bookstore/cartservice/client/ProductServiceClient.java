package com.bookstore.cartservice.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.math.BigDecimal;
import java.util.Map;
@FeignClient(name="PRODUCT-SERVICE", path="/api/products")
public interface ProductServiceClient {
    @GetMapping("/{id}")
    Map<String,Object> getProduct(@PathVariable Long id);
}