package com.guisebastiao.ecommerceapi.validation.FileCotentType;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileContentTypeValidator.class)
public @interface FileContentType {
    String[] allowed();
    String message() default "Tipo de arquivo não suportado";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
