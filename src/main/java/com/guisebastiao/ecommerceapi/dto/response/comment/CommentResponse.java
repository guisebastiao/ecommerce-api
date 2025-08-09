package com.guisebastiao.ecommerceapi.dto.response.comment;

import com.guisebastiao.ecommerceapi.dto.response.client.ClientSimpleResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentResponse(
        UUID commentId,
        String content,
        LocalDateTime createdAt,
        ClientSimpleResponse client
) { }
