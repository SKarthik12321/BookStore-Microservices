package com.bookstore.cartservice.model;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CartItem implements Serializable {
    private Long productId;
    private String productTitle;
    private int quantity;
    private BigDecimal unitPrice;
    public BigDecimal getSubtotal(){ return unitPrice.multiply(BigDecimal.valueOf(quantity)); }
}