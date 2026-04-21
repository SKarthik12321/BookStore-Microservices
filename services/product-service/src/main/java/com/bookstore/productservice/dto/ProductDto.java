package com.bookstore.productservice.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
public class ProductDto {
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Request {
        @NotBlank private String title;
        @NotBlank private String author;
        private String isbn;
        @NotNull @DecimalMin("0.0") private BigDecimal price;
        @NotNull @Min(0) private Integer stockQuantity;
        private String imageUrl;
        private String description;
        private Long categoryId;
    }
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private Long id;
        private String title, author, isbn, imageUrl, description;
        private BigDecimal price;
        private Integer stockQuantity;
        private String categoryName;
        private Long categoryId;
        private LocalDateTime createdAt;
    }
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class CategoryRequest {
        @NotBlank private String name;
        private String description;
    }
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class CategoryResponse {
        private Long id;
        private String name, description;
    }
}