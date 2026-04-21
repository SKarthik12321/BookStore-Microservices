package com.bookstore.wishlistservice.service;
import com.bookstore.wishlistservice.client.ProductServiceClient;
import com.bookstore.wishlistservice.entity.WishlistItem;
import com.bookstore.wishlistservice.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class WishlistService {
    @Autowired private WishlistRepository wishlistRepository;
    @Autowired private ProductServiceClient productServiceClient;
    public List<WishlistItem> getWishlist(String userId){ return wishlistRepository.findByUserId(userId); }
    public WishlistItem addToWishlist(String userId,Long productId){
        if(wishlistRepository.findByUserIdAndProductId(userId,productId).isPresent())
            throw new IllegalArgumentException("Product already in wishlist");
        var product=productServiceClient.getProduct(productId);
        return wishlistRepository.save(WishlistItem.builder().userId(userId).productId(productId).productTitle(product.get("title").toString()).build());
    }
    @Transactional public void removeFromWishlist(String userId,Long productId){ wishlistRepository.deleteByUserIdAndProductId(userId,productId); }
    @Transactional public void clearWishlist(String userId){ wishlistRepository.deleteByUserId(userId); }
}