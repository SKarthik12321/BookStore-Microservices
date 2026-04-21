package com.bookstore.orderservice.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity @Table(name="orders") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Order {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String userId;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private OrderStatus status;
    @OneToMany(mappedBy="order",cascade=CascadeType.ALL,fetch=FetchType.EAGER) @Builder.Default
    private List<OrderItem> items = new ArrayList<>();
    private BigDecimal totalAmount;
    private String shippingAddress;
    @Column(updatable=false) private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @PrePersist protected void onCreate(){ createdAt=LocalDateTime.now(); updatedAt=LocalDateTime.now(); if(status==null)status=OrderStatus.PENDING; }
    @PreUpdate  protected void onUpdate(){ updatedAt=LocalDateTime.now(); }
}