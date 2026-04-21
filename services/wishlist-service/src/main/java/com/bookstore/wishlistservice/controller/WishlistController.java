package com.bookstore.wishlistservice.controller;
import com.bookstore.wishlistservice.entity.WishlistItem;
import com.bookstore.wishlistservice.service.WishlistService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/wishlist") @Tag(name="Wishlist")
public class WishlistController {
    @Autowired private WishlistService wishlistService;
    @GetMapping public ResponseEntity<List<WishlistItem>> get(Authentication auth){ return ResponseEntity.ok(wishlistService.getWishlist(auth.getName())); }
    @PostMapping("/add/{productId}") public ResponseEntity<WishlistItem> add(Authentication auth,@PathVariable Long productId){ return ResponseEntity.status(201).body(wishlistService.addToWishlist(auth.getName(),productId)); }
    @DeleteMapping("/remove/{productId}") public ResponseEntity<Void> remove(Authentication auth,@PathVariable Long productId){ wishlistService.removeFromWishlist(auth.getName(),productId); return ResponseEntity.noContent().build(); }
    @DeleteMapping("/clear") public ResponseEntity<Void> clear(Authentication auth){ wishlistService.clearWishlist(auth.getName()); return ResponseEntity.noContent().build(); }
}