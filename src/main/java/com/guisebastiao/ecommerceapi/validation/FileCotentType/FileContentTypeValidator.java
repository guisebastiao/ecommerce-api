package com.guisebastiao.ecommerceapi.validation.FileCotentType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, Object> {

    private List<String> allowedTypes;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        this.allowedTypes = Arrays.asList(constraintAnnotation.allowed());
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return switch (value) {
            case null -> true;
            case MultipartFile file -> allowedTypes.contains(file.getContentType());
            case List<?> list -> list.stream()
                    .filter(item -> item instanceof MultipartFile)
                    .map(item -> (MultipartFile) item)
                    .allMatch(file -> allowedTypes.contains(file.getContentType()));
            default -> false;
        };

    }
}
