package com.guisebastiao.ecommerceapi.dto.response.discount;

import java.time.LocalDateTime;
import java.util.UUID;

public record DiscountResponse(
        UUID discountId,
        String name,
        Double percent,
        LocalDateTime endDate
) { }
