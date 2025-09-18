package com.guisebastiao.ecommerceapi.validation.passwordMather;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {PasswordMatchesResetPasswordValidator.class, PasswordMatchesRegisterValidator.class, PasswordMatchesUpdatePasswordValidator.class})
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatches {
    String message() default "As senhas não coincidem";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
