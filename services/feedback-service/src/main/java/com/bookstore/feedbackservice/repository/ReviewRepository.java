package com.bookstore.feedbackservice.repository;
import com.bookstore.feedbackservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByProductId(Long productId);
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.productId=:id") Optional<Double> avgRating(@Param("id") Long id);
    Optional<Review> findByIdAndUserId(Long id, String userId);
}