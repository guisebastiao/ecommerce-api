package com.guisebastiao.ecommerceapi.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDTO(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        Boolean available
) { }
