package com.guisebastiao.ecommerceapi.dto.request.order;

import com.guisebastiao.ecommerceapi.enums.PaymentMethod;
import com.guisebastiao.ecommerceapi.validation.ValidateEnum.ValidateEnum;
import jakarta.validation.constraints.NotNull;

public record OrderRequest(
        @NotNull(message = "Informe o métado de pagamento")
        @ValidateEnum(enumClass = PaymentMethod.class, message = "Esse métado de pagamento não é válido")
        String paymentMethod
) { }
