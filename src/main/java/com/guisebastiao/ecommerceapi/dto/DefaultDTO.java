package com.guisebastiao.ecommerceapi.dto;

public record DefaultDTO<T>(
        Boolean success,
        String message,
        T data
){ }