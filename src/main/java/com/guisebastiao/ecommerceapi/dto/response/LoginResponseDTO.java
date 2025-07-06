package com.guisebastiao.ecommerceapi.dto.response;

import java.util.UUID;

public record LoginResponseDTO(
        UUID clientId,
        String email
) { }
