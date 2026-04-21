package com.bookstore.cartservice.model;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@RedisHash("cart") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Cart implements Serializable {
    @Id private String userId;
    @Builder.Default private List<CartItem> items = new ArrayList<>();
    public BigDecimal getTotalAmount(){
        return items.stream().map(CartItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}