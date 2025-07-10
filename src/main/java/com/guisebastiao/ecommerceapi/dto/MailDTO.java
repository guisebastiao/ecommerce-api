package com.guisebastiao.ecommerceapi.dto;

public record MailDTO(
        String to,
        String subject,
        String template
) { }
