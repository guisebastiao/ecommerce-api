package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.PageResponseDTO;
import com.guisebastiao.ecommerceapi.dto.request.ReviewRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.ReviewResponseDTO;

public interface ReviewService {
    DefaultDTO<Void> createReview(String productId, ReviewRequestDTO reviewRequestDTO);
    DefaultDTO<PageResponseDTO<ReviewResponseDTO>> findAllReviews(String productId, int offset, int limit);
    DefaultDTO<Void> deleteReview(String reviewId);
}
