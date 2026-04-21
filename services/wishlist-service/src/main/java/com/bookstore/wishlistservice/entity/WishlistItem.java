package com.bookstore.wishlistservice.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity @Table(name="wishlist_items") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class WishlistItem {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String userId;
    @Column(nullable=false) private Long productId;
    private String productTitle;
    @Column(updatable=false) private LocalDateTime addedAt;
    @PrePersist protected void onCreate(){ addedAt=LocalDateTime.now(); }
}