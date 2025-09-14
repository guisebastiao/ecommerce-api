package com.guisebastiao.ecommerceapi.dto.response.order;

import com.guisebastiao.ecommerceapi.dto.response.product.ProductResponse;

import java.util.UUID;

public record OrderItemResponse(
        UUID orderItemId,
        int quantity,
        ProductResponse product
) { }
