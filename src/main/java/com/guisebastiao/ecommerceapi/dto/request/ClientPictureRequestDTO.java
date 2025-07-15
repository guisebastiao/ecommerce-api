package com.guisebastiao.ecommerceapi.dto.request;

import com.guisebastiao.ecommerceapi.validation.FileCotentType.FileContentType;
import com.guisebastiao.ecommerceapi.validation.FileSize.FileSize;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record ClientPictureRequestDTO(
        @NotNull(message = "A imagem de perfil é obrigatória")
        @FileSize(max = 5 * 1024 * 1024, message = "A imagem de perfil deve ter no máximo 5MB")
        @FileContentType(allowed = {"image/jpeg", "image/jpg", "image/png", "image/webp", "image/heic", "image/heif"}, message = "Arquivo não permitido")
        MultipartFile file
) { }
