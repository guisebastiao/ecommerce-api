package com.guisebastiao.ecommerceapi.dto.response;

import java.util.UUID;

public record AddressResponseDTO(
        UUID id,
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String zip,
        String country
) { }
