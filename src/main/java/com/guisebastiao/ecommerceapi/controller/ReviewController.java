package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.PageResponseDTO;
import com.guisebastiao.ecommerceapi.dto.PaginationFilterDTO;
import com.guisebastiao.ecommerceapi.dto.request.ReviewRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.ReviewResponseDTO;
import com.guisebastiao.ecommerceapi.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/{productId}")
    public ResponseEntity<DefaultDTO<Void>> createReview(@PathVariable String productId, @RequestBody @Valid ReviewRequestDTO reviewRequestDTO) {
        DefaultDTO<Void> response = this.reviewService.createReview(productId, reviewRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<DefaultDTO<PageResponseDTO<ReviewResponseDTO>>> findAllReviews(@PathVariable String productId, @Valid PaginationFilterDTO pagination) {
        DefaultDTO<PageResponseDTO<ReviewResponseDTO>> response = this.reviewService.findAllReviews(productId, pagination.offset(), pagination.limit());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<DefaultDTO<Void>> deleteReview(@PathVariable String reviewId) {
        DefaultDTO<Void> response = this.reviewService.deleteReview(reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
