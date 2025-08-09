package com.guisebastiao.ecommerceapi.dto.response.review;

import com.guisebastiao.ecommerceapi.dto.response.client.ClientSimpleResponse;

import java.util.UUID;

public record ReviewResponse(
        UUID reviewId,
        Integer rating,
        ClientSimpleResponse client
) { }
