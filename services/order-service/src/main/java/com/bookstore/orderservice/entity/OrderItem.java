package com.bookstore.orderservice.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
@Entity @Table(name="order_items") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderItem {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="order_id") private Order order;
    private Long productId;
    private String productTitle;
    private int quantity;
    private BigDecimal unitPrice;
    public BigDecimal getSubtotal(){ return unitPrice.multiply(BigDecimal.valueOf(quantity)); }
}