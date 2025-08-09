package com.guisebastiao.ecommerceapi.dto.response.client;

import com.guisebastiao.ecommerceapi.dto.response.clientPicture.ClientPictureResponse;

import java.util.UUID;

public record ClientSimpleResponse(
        UUID clientId,
        String name,
        String surname,
        ClientPictureResponse clientPicture
) { }
