package com.guisebastiao.ecommerceapi.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class CodeGenerator {
    public String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        int byteLength = 32;
        byte[] tokenBytes = new byte[byteLength];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
}