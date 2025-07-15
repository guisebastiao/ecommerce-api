package com.guisebastiao.ecommerceapi.validation.FileCotentType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<String> allowedTypes;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        this.allowedTypes = Arrays.asList(constraintAnnotation.allowed());
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        return file == null || allowedTypes.contains(file.getContentType());
    }
}
