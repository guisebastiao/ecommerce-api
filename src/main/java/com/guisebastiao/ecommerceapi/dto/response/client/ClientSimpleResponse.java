package com.guisebastiao.ecommerceapi.dto.response.client;

import com.guisebastiao.ecommerceapi.dto.response.clientPicture.ClientPictureResponse;
import com.guisebastiao.ecommerceapi.enums.Role;

import java.util.UUID;

public record ClientSimpleResponse(
        UUID clientId,
        String name,
        String surname,
        Role role,
        ClientPictureResponse clientPicture
) { }
