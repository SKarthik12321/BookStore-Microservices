package com.bookstore.feedbackservice.controller;
import com.bookstore.feedbackservice.dto.ReviewDto;
import com.bookstore.feedbackservice.service.FeedbackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/feedback") @Tag(name="Feedback")
public class FeedbackController {
    @Autowired private FeedbackService feedbackService;
    @PostMapping public ResponseEntity<ReviewDto.Response> submit(Authentication auth,@Valid @RequestBody ReviewDto.Request req){
        return ResponseEntity.status(201).body(feedbackService.submit(auth.getName(),auth.getName(),req));
    }
    @GetMapping("/product/{id}") public ResponseEntity<List<ReviewDto.Response>> getByProduct(@PathVariable Long id){ return ResponseEntity.ok(feedbackService.getByProduct(id)); }
    @GetMapping("/product/{id}/rating") public ResponseEntity<Double> getRating(@PathVariable Long id){ return ResponseEntity.ok(feedbackService.getAvgRating(id)); }
    @PutMapping("/{id}") public ResponseEntity<ReviewDto.Response> edit(Authentication auth,@PathVariable Long id,@Valid @RequestBody ReviewDto.Request req){ return ResponseEntity.ok(feedbackService.edit(id,auth.getName(),req)); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){ feedbackService.delete(id); return ResponseEntity.noContent().build(); }
}