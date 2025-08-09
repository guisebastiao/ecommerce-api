package com.guisebastiao.ecommerceapi.dto.response.clientPicture;

import java.util.UUID;

public record ClientPictureResponse(
        UUID clientPictureId,
        String objectId,
        String url
) { }
