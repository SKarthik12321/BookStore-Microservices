package com.bookstore.cartservice.service;
import com.bookstore.cartservice.client.ProductServiceClient;
import com.bookstore.cartservice.dto.CartDto;
import com.bookstore.cartservice.model.Cart;
import com.bookstore.cartservice.model.CartItem;
import com.bookstore.cartservice.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class CartService {
    @Autowired private CartRepository cartRepository;
    @Autowired private ProductServiceClient productServiceClient;

    private Cart getOrCreate(String userId){
        return cartRepository.findById(userId).orElse(Cart.builder().userId(userId).build());
    }
    public CartDto.CartResponse getCart(String userId){
        return toResponse(getOrCreate(userId));
    }
    public CartDto.CartResponse addItem(String userId, CartDto.AddItemRequest req){
        Cart cart = getOrCreate(userId);
        Map<String,Object> product = productServiceClient.getProduct(req.getProductId());
        BigDecimal price = new BigDecimal(product.get("price").toString());
        String title = product.get("title").toString();
        cart.getItems().stream().filter(i->i.getProductId().equals(req.getProductId())).findFirst()
            .ifPresentOrElse(
                i->i.setQuantity(i.getQuantity()+req.getQuantity()),
                ()->cart.getItems().add(CartItem.builder().productId(req.getProductId()).productTitle(title).quantity(req.getQuantity()).unitPrice(price).build())
            );
        return toResponse(cartRepository.save(cart));
    }
    public CartDto.CartResponse updateItem(String userId, CartDto.UpdateItemRequest req){
        Cart cart = getOrCreate(userId);
        if(req.getQuantity()==0){
            cart.getItems().removeIf(i->i.getProductId().equals(req.getProductId()));
        } else {
            cart.getItems().stream().filter(i->i.getProductId().equals(req.getProductId())).findFirst()
                .ifPresent(i->i.setQuantity(req.getQuantity()));
        }
        return toResponse(cartRepository.save(cart));
    }
    public CartDto.CartResponse removeItem(String userId, Long productId){
        Cart cart = getOrCreate(userId);
        cart.getItems().removeIf(i->i.getProductId().equals(productId));
        return toResponse(cartRepository.save(cart));
    }
    public void clearCart(String userId){
        Cart cart = getOrCreate(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
    public BigDecimal getTotal(String userId){
        return getOrCreate(userId).getTotalAmount();
    }
    private CartDto.CartResponse toResponse(Cart cart){
        List<CartDto.ItemResponse> items = cart.getItems().stream().map(i->
            CartDto.ItemResponse.builder().productId(i.getProductId()).productTitle(i.getProductTitle())
                .quantity(i.getQuantity()).unitPrice(i.getUnitPrice()).subtotal(i.getSubtotal()).build()
        ).collect(Collectors.toList());
        return CartDto.CartResponse.builder().userId(cart.getUserId()).items(items).totalAmount(cart.getTotalAmount()).build();
    }
}