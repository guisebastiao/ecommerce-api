package com.guisebastiao.ecommerceapi.dto;

public record DefaultResponse<T>(
        boolean success,
        String message,
        T data
){ }