package com.guisebastiao.ecommerceapi.dto.response;

import java.util.UUID;

public record ClientPictureResponseDTO(
        UUID id,
        String objectId,
        String url
) { }
