package com.guisebastiao.ecommerceapi.dto.response.productPicture;

import java.util.UUID;

public record ProductPictureResponse(
        UUID productPictureId,
        String objectId,
        String url
) { }
