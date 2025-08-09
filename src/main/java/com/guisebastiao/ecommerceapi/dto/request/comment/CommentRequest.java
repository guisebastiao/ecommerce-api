package com.guisebastiao.ecommerceapi.dto.request.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentRequest(
        @NotBlank(message = "Informe o comentário")
        @Size(max = 300, message = "O comentário pode ter no máximo 300 caracteres")
        String content
) { }
