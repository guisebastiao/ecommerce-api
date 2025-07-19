package com.guisebastiao.ecommerceapi.dto.response;

import com.guisebastiao.ecommerceapi.dto.request.DiscountRequestDTO;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDTO(
        UUID id,
        String name,
        String description,
        BigDecimal originalPrice,
        BigDecimal price,
        Integer stock,
        Boolean available,
        Boolean haveDiscount,
        CategoryResponseDTO category,
        DiscountRequestDTO discount
) { }
