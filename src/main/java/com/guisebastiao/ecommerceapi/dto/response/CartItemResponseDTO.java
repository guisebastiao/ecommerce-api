package com.guisebastiao.ecommerceapi.dto.response;

import java.util.UUID;

public record CartItemResponseDTO(
        UUID id,
        Integer quantity,
        ProductResponseDTO product
) { }
