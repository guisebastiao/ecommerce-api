package com.guisebastiao.ecommerceapi.util;

import com.guisebastiao.ecommerceapi.exception.BadRequestException;

public class LongConverter {
    public static Long toLong(String id) {
        try {
            return Long.parseLong(id);
        } catch (Exception e) {
            throw new BadRequestException("O id está inválido");
        }
    }
}