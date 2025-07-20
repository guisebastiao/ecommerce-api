package com.guisebastiao.ecommerceapi.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record DiscountResponseDTO(
        UUID id,
        String name,
        Double percent,
        LocalDateTime endDate
) { }
