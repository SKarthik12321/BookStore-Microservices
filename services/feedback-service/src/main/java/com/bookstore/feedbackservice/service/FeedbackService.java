package com.bookstore.feedbackservice.service;
import com.bookstore.feedbackservice.dto.ReviewDto;
import com.bookstore.feedbackservice.entity.Review;
import com.bookstore.feedbackservice.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class FeedbackService {
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private KafkaTemplate<String,Object> kafkaTemplate;
    public ReviewDto.Response submit(String userId,String userName,ReviewDto.Request req){
        Review r=Review.builder().productId(req.getProductId()).userId(userId).userName(userName).comment(req.getComment()).rating(req.getRating()).build();
        Review saved=reviewRepository.save(r);
        try{ kafkaTemplate.send("review-events",Map.of("type","REVIEW_ADDED","productId",req.getProductId())); }catch(Exception ignored){}
        return toResponse(saved);
    }
    public List<ReviewDto.Response> getByProduct(Long productId){ return reviewRepository.findByProductId(productId).stream().map(this::toResponse).collect(Collectors.toList()); }
    public Double getAvgRating(Long productId){ return reviewRepository.avgRating(productId).orElse(0.0); }
    public ReviewDto.Response edit(Long id,String userId,ReviewDto.Request req){
        Review r=reviewRepository.findByIdAndUserId(id,userId).orElseThrow(()->new EntityNotFoundException("Review not found"));
        r.setComment(req.getComment()); r.setRating(req.getRating());
        return toResponse(reviewRepository.save(r));
    }
    public void delete(Long id){ reviewRepository.deleteById(id); }
    private ReviewDto.Response toResponse(Review r){
        return ReviewDto.Response.builder().id(r.getId()).productId(r.getProductId()).userId(r.getUserId()).userName(r.getUserName()).comment(r.getComment()).rating(r.getRating()).createdAt(r.getCreatedAt()).build();
    }
}