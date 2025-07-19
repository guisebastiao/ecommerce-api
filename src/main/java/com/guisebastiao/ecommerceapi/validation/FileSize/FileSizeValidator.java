package com.guisebastiao.ecommerceapi.validation.FileSize;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileSizeValidator implements ConstraintValidator<FileSize, Object> {
    private long max;

    @Override
    public void initialize(FileSize constraintAnnotation) {
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return switch (value) {
            case null -> true;
            case MultipartFile file -> file.getSize() <= max;
            case List<?> list -> list.stream()
                    .filter(item -> item instanceof MultipartFile)
                    .map(item -> (MultipartFile) item)
                    .allMatch(file -> file.getSize() <= max);
            default -> false;
        };

    }
}