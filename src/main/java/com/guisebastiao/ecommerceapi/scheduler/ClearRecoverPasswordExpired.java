package com.guisebastiao.ecommerceapi.scheduler;

import com.guisebastiao.ecommerceapi.repository.RecoverPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ClearRecoverPasswordExpired {

    @Autowired
    private RecoverPasswordRepository recoverPasswordRepository;

    @Scheduled(fixedRate = 60000)
    public void clear() {
        LocalDateTime now = LocalDateTime.now();
        this.recoverPasswordRepository.deleteAllByRecoverPasswordExpired(now);
    }
}
