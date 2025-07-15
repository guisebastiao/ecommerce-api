package com.guisebastiao.ecommerceapi.validation.FileSize;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileSizeValidator.class)
public @interface FileSize {
    String message() default "Tamanho de arquivo inválido";
    long max();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
