package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.MailDTO;

public interface MailService {
    void sendEmail(MailDTO mailDTO);
}
