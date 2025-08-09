package com.guisebastiao.ecommerceapi.validation.passwordMather;

import com.guisebastiao.ecommerceapi.dto.request.auth.RegisterRequest;
import com.guisebastiao.ecommerceapi.dto.request.recoverPassword.ResetPasswordRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesRegisterValidator implements ConstraintValidator<PasswordMatches, RegisterRequest> {

    @Override
    public boolean isValid(RegisterRequest dto, ConstraintValidatorContext context) {
        if (dto == null) return true;

        boolean valid = dto.password().equals(dto.confirmPassword());

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("As senhas não coincidem")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }

        return valid;
    }
}
