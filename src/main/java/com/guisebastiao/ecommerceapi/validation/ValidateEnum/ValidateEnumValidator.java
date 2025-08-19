package com.guisebastiao.ecommerceapi.validation.ValidateEnum;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Stream;

public class ValidateEnumValidator implements ConstraintValidator<ValidateEnum, CharSequence> {
    private List<String> acceptedValues;

    @Override
    public void initialize(ValidateEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return acceptedValues.contains(value.toString());
    }
}
