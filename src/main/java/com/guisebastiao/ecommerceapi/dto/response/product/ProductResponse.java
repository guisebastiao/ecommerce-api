package com.guisebastiao.ecommerceapi.dto.response.product;

import com.guisebastiao.ecommerceapi.dto.request.discount.DiscountRequest;
import com.guisebastiao.ecommerceapi.dto.response.productPicture.ProductPictureResponse;
import com.guisebastiao.ecommerceapi.dto.response.category.CategoryResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductResponse(
        UUID productId,
        String name,
        String description,
        BigDecimal originalPrice,
        BigDecimal price,
        int stock,
        int totalComments,
        Boolean available,
        Boolean haveDiscount,
        Boolean alreadyReviewed,
        Boolean alreadyCommented,
        Boolean isFavorite,
        CategoryResponse category,
        DiscountRequest discount,
        Double reviewRating,
        List<ProductPictureResponse> productPictures
) { }
