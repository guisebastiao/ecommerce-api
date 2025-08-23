package com.guisebastiao.ecommerceapi.dto.response.client;

import com.guisebastiao.ecommerceapi.dto.response.clientPicture.ClientPictureResponse;
import com.guisebastiao.ecommerceapi.enums.Role;

import java.time.LocalDate;
import java.util.UUID;

public record ClientResponse(
        UUID clientId,
        String name,
        String surname,
        String cpf,
        String phone,
        LocalDate birth,
        String email,
        Role role,
        ClientPictureResponse clientPicture
) { }
