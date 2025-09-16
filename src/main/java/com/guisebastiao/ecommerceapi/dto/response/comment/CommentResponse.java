package com.guisebastiao.ecommerceapi.dto.response.comment;

import com.guisebastiao.ecommerceapi.dto.response.client.ClientSimpleResponse;

import java.time.Instant;
import java.util.UUID;

public record CommentResponse(
        UUID commentId,
        String content,
        boolean belongsToAuthUser,
        Instant createdAt,
        ClientSimpleResponse client
) { }
