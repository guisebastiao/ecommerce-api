package com.guisebastiao.ecommerceapi.dto.response.address;

import java.util.UUID;

public record AddressResponse(
        UUID addressId,
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String zip,
        String country
) { }
