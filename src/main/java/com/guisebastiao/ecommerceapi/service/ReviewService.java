package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.request.review.ReviewRequest;
import com.guisebastiao.ecommerceapi.dto.response.review.ReviewResponse;

public interface ReviewService {
    DefaultResponse<Void> createReview(String productId, ReviewRequest reviewRequest);
    DefaultResponse<PageResponse<ReviewResponse>> findAllReviews(String productId, int offset, int limit);
    DefaultResponse<Void> deleteReview(String productId);
}
