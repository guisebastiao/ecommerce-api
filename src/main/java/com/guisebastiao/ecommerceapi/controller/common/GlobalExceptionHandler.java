package com.guisebastiao.ecommerceapi.controller.common;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.FieldErrorDTO;
import com.guisebastiao.ecommerceapi.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultDTO<List<FieldErrorDTO>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();

        List<FieldErrorDTO> fieldErrorDTOs = fieldErrors.stream()
                .map(fe -> new FieldErrorDTO(fe.getField(), fe.getDefaultMessage()))
                .toList();

        DefaultDTO<List<FieldErrorDTO>> response = new DefaultDTO<List<FieldErrorDTO>>(Boolean.FALSE, "Erro de validação", fieldErrorDTOs);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    @ExceptionHandler(ConflictEntityException.class)
    public ResponseEntity<DefaultDTO<Void>> handleDuplicateEntityException(ConflictEntityException e) {
        DefaultDTO<Void> response = new DefaultDTO<Void>(Boolean.FALSE, e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<DefaultDTO<Void>> handleEntityNotFoundException(EntityNotFoundException e) {
        DefaultDTO<Void> response = new DefaultDTO<Void>(Boolean.FALSE, e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<DefaultDTO<Void>> handleBadRequestException(BadRequestException e) {
        DefaultDTO<Void> response = new DefaultDTO<Void>(Boolean.FALSE, e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<DefaultDTO<Void>> handleUnauthorizedException(Exception e) {
        DefaultDTO<Void> response = new DefaultDTO<Void>(Boolean.FALSE, e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<DefaultDTO<Void>> handleForbiddenException(Exception e) {
        DefaultDTO<Void> response = new DefaultDTO<Void>(Boolean.FALSE, e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(FailedUploadFileException.class)
    public ResponseEntity<DefaultDTO<Void>> handleFailedUploadFileException(Exception e) {
        DefaultDTO<Void> response = new DefaultDTO(Boolean.FALSE, e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<DefaultDTO<Void>> handleNotFound(NoHandlerFoundException e) {
        DefaultDTO<Void> response = new DefaultDTO<Void>(Boolean.FALSE, "Rota não encontrada", null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<DefaultDTO<Void>> handleRuntimeException(RuntimeException e) {
        logger.error("Erro inesperado", e);
        DefaultDTO<Void> response = new DefaultDTO<Void>(Boolean.FALSE, "Ocorreu um erro inesperado, tente novamente mais tarde", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
