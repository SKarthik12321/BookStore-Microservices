package com.bookstore.cartservice.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;
public class CartDto {
    @Data @NoArgsConstructor @AllArgsConstructor
    public static class AddItemRequest {
        @NotNull private Long productId;
        @Min(1) private int quantity;
    }
    @Data @NoArgsConstructor @AllArgsConstructor
    public static class UpdateItemRequest {
        @NotNull private Long productId;
        @Min(0) private int quantity;
    }
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class CartResponse {
        private String userId;
        private List<ItemResponse> items;
        private BigDecimal totalAmount;
    }
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ItemResponse {
        private Long productId;
        private String productTitle;
        private int quantity;
        private BigDecimal unitPrice;
        private BigDecimal subtotal;
    }
}