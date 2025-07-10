package com.guisebastiao.ecommerceapi.validation.passwordMather;

import com.guisebastiao.ecommerceapi.dto.request.ResetPasswordRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, ResetPasswordRequestDTO> {

    @Override
    public boolean isValid(ResetPasswordRequestDTO dto, ConstraintValidatorContext context) {
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
