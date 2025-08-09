package com.guisebastiao.ecommerceapi.validation.passwordMather;

import com.guisebastiao.ecommerceapi.dto.request.recoverPassword.ResetPasswordRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesResetPasswordValidator implements ConstraintValidator<PasswordMatches, ResetPasswordRequest> {

    @Override
    public boolean isValid(ResetPasswordRequest dto, ConstraintValidatorContext context) {
        if (dto == null) return true;

        boolean valid = dto.newPassword().equals(dto.confirmPassword());

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("As senhas não coincidem")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }

        return valid;
    }
}
