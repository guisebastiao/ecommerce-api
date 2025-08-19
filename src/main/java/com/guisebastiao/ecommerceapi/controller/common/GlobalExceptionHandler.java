package com.guisebastiao.ecommerceapi.controller.common;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.FieldErrorResponse;
import com.guisebastiao.ecommerceapi.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultResponse<List<FieldErrorResponse>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();

        List<FieldErrorResponse> fieldErrorDTOs = fieldErrors.stream()
                .map(fe -> new FieldErrorResponse(fe.getField(), fe.getDefaultMessage()))
                .toList();

        DefaultResponse<List<FieldErrorResponse>> response = new DefaultResponse<List<FieldErrorResponse>>(false, "Erro de validação", fieldErrorDTOs);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    @ExceptionHandler(ConflictEntityException.class)
    public ResponseEntity<DefaultResponse<Void>> handleDuplicateEntityException(ConflictEntityException e) {
        DefaultResponse<Void> response = new DefaultResponse<Void>(false, e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<DefaultResponse<Void>> handleEntityNotFoundException(EntityNotFoundException e) {
        DefaultResponse<Void> response = new DefaultResponse<Void>(false, e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<DefaultResponse<Void>> handleBadRequestException(BadRequestException e) {
        DefaultResponse<Void> response = new DefaultResponse<Void>(false, e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<DefaultResponse<Void>> handleUnauthorizedException(UnauthorizedException e) {
        DefaultResponse<Void> response = new DefaultResponse<Void>(false, e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<DefaultResponse<Void>> handleForbiddenException(ForbiddenException e) {
        DefaultResponse<Void> response = new DefaultResponse<Void>(false, e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(FailedUploadFileException.class)
    public ResponseEntity<DefaultResponse<Void>> handleFailedUploadFileException(FailedUploadFileException e) {
        logger.error("Error uploading file", e);
        DefaultResponse<Void> response = new DefaultResponse<Void>(false, e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
    }

    @ExceptionHandler(BadGatewayException.class)
    public ResponseEntity<DefaultResponse<Void>> handlePaymentFailureException(BadGatewayException e) {
        logger.error("Error bad gateway", e);
        DefaultResponse<Void> response = new DefaultResponse<Void>(false, e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<DefaultResponse<Void>> handleNotFound(NoHandlerFoundException e) {
        DefaultResponse<Void> response = new DefaultResponse<Void>(false, "Rota não encontrada", null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<DefaultResponse<Void>> handleRuntimeException(RuntimeException e) {
        logger.error("unexpected error", e);
        DefaultResponse<Void> response = new DefaultResponse<Void>(false, "Ocorreu um erro inesperado, tente novamente mais tarde", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
