package com.guisebastiao.ecommerceapi.dto.response.cart;

import com.guisebastiao.ecommerceapi.dto.response.product.ProductResponse;

import java.util.UUID;

public record CartItemResponse(
        UUID cartItemId,
        Integer quantity,
        ProductResponse product
) { }
