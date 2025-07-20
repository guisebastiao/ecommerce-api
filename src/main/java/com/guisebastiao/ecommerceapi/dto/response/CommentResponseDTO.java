package com.guisebastiao.ecommerceapi.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentResponseDTO(
        UUID id,
        String content,
        LocalDateTime createdAt,
        ClientSimpleResponseDTO client
) { }
