package com.guisebastiao.ecommerceapi.dto.response;

import java.util.UUID;

public record ReviewResponseDTO(
        UUID id,
        Integer rating,
        ClientSimpleResponseDTO client
) { }
