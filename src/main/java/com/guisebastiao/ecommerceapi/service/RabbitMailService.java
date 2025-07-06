package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.MailDTO;

public interface RabbitMailService {
    void producer(MailDTO mailDTO);
}
