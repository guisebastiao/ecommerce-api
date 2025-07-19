package com.guisebastiao.ecommerceapi.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record DiscountRequestDTO(
        @NotBlank(message = "Informe o nome da promoção")
        @Size(max = 250, message = "O nome da promoção tem que ser menor de 250 caracteres")
        String name,

        @NotNull(message = "Informe o valor do desconto")
        @DecimalMin(value = "0.01", message = "O percentual mínimo é 0.01")
        @DecimalMax(value = "100.0", message = "O percentual máximo é 100.0")
        Double percent,

        @Future
        @NotNull(message = "Informe o início da promoção")
        LocalDateTime startDate,

        @Future
        @NotNull(message = "Informe o fim da promoção")
        LocalDateTime endDate,

                Boolean active
) { }
