package com.guisebastiao.ecommerceapi.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record ClientSimpleResponseDTO(
        UUID id,
        String name,
        String surname,
        ClientPictureResponseDTO clientPicture
) { }
