package com.bookstore.feedbackservice.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity @Table(name="reviews") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Review {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private Long productId;
    @Column(nullable=false) private String userId;
    @Column(nullable=false) private String userName;
    @Column(nullable=false,columnDefinition="TEXT") private String comment;
    @Column(nullable=false) private Integer rating;
    @Column(updatable=false) private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @PrePersist protected void onCreate(){ createdAt=LocalDateTime.now(); updatedAt=LocalDateTime.now(); }
    @PreUpdate  protected void onUpdate(){ updatedAt=LocalDateTime.now(); }
}