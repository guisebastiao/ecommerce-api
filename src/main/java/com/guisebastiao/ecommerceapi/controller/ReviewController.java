package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.PaginationFilter;
import com.guisebastiao.ecommerceapi.dto.request.review.ReviewRequest;
import com.guisebastiao.ecommerceapi.dto.response.review.ReviewResponse;
import com.guisebastiao.ecommerceapi.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/{productId}")
    public ResponseEntity<DefaultResponse<Void>> createReview(@PathVariable String productId, @RequestBody @Valid ReviewRequest reviewRequest) {
        DefaultResponse<Void> response = this.reviewService.createReview(productId, reviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<DefaultResponse<PageResponse<ReviewResponse>>> findAllReviews(@PathVariable String productId, @Valid PaginationFilter pagination) {
        DefaultResponse<PageResponse<ReviewResponse>> response = this.reviewService.findAllReviews(productId, pagination.offset(), pagination.limit());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<DefaultResponse<Void>> deleteReview(@PathVariable String productId) {
        DefaultResponse<Void> response = this.reviewService.deleteReview(productId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
