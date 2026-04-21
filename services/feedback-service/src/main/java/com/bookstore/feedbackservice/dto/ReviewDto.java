package com.bookstore.feedbackservice.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
public class ReviewDto {
    @Data @NoArgsConstructor @AllArgsConstructor
    public static class Request {
        @NotNull private Long productId;
        @NotBlank private String comment;
        @Min(1) @Max(5) @NotNull private Integer rating;
    }
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private Long id;
        private Long productId;
        private String userId,userName,comment;
        private Integer rating;
        private LocalDateTime createdAt;
    }
}