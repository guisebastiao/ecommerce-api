package com.guisebastiao.ecommerceapi.exception;

public class FailedUploadFileException extends RuntimeException {
    public FailedUploadFileException(String message, Throwable cause) {
        super(message, cause);
    }
}