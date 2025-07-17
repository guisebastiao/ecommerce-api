package com.guisebastiao.ecommerceapi.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record ClientResponseDTO(
        UUID id,
        String name,
        String surname,
        String cpf,
        String phone,
        LocalDate birth,
        String email,
        ClientPictureResponseDTO clientPicture
) { }
