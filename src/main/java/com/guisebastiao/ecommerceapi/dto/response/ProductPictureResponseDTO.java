package com.guisebastiao.ecommerceapi.dto.response;

import java.util.UUID;

public record ProductPictureResponseDTO(
        UUID id,
        String objectId,
        String url
) { }
