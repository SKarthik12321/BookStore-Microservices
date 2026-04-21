package com.bookstore.orderservice.dto;
import com.bookstore.orderservice.entity.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
public class OrderDto {
    @Data @NoArgsConstructor @AllArgsConstructor
    public static class PlaceOrderRequest {
        @NotBlank private String shippingAddress;
    }
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class OrderResponse {
        private Long id;
        private String userId;
        private OrderStatus status;
        private List<ItemResponse> items;
        private BigDecimal totalAmount;
        private String shippingAddress;
        private LocalDateTime createdAt;
    }
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ItemResponse {
        private Long productId;
        private String productTitle;
        private int quantity;
        private BigDecimal unitPrice;
        private BigDecimal subtotal;
    }
    @Data @NoArgsConstructor @AllArgsConstructor
    public static class UpdateStatusRequest {
        private OrderStatus status;
    }
}