package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.config.RabbitMQConfig;
import com.guisebastiao.ecommerceapi.dto.MailDTO;
import com.guisebastiao.ecommerceapi.service.MailService;
import com.guisebastiao.ecommerceapi.service.RabbitMailService;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMailServiceImpl implements RabbitMailService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MailService mailService;

    @Transactional
    @RabbitListener(queues = RabbitMQConfig.QUEUE_EMAILS)
    public void consumer(MailDTO mailDTO) {
        mailService.sendEmail(mailDTO);
    }

    @Override
    @Transactional
    public void producer(MailDTO mailDTO) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_EMAILS, mailDTO);
    }
}
