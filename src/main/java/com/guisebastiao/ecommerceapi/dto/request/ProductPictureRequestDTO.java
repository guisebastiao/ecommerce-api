package com.guisebastiao.ecommerceapi.dto.request;

import com.guisebastiao.ecommerceapi.validation.FileCotentType.FileContentType;
import com.guisebastiao.ecommerceapi.validation.FileSize.FileSize;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ProductPictureRequestDTO(
        @NotNull(message = "Uma imagem do produto é obrigatória")
        @FileSize(max = 5 * 1024 * 1024, message = "A imagem do produto deve ter no máximo 5MB")
        @FileContentType(allowed = {"image/jpeg", "image/jpg", "image/png"}, message = "Arquivo não permitido")
        List<MultipartFile> files
) { }
