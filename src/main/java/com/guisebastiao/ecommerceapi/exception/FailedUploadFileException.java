package com.guisebastiao.ecommerceapi.exception;

public class FailedUploadFileException extends RuntimeException {
    public FailedUploadFileException(String message) {
        super(message);
    }
}