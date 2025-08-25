package com.guisebastiao.ecommerceapi.dto.response.auth;

import com.guisebastiao.ecommerceapi.dto.response.client.ClientSimpleResponse;

import java.time.Instant;

public record ActiveLoginResponse(
        Instant expiresAccessToken,
        Instant expiresRefreshToken,
        ClientSimpleResponse client
) { }
