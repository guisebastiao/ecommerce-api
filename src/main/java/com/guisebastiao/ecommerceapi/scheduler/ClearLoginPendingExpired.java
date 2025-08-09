package com.guisebastiao.ecommerceapi.scheduler;

import com.guisebastiao.ecommerceapi.repository.LoginPendingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class ClearLoginPendingExpired {

    @Autowired
    private LoginPendingRepository loginPendingRepository;

    @Scheduled(fixedRate = 60000)
    public void clear() {
        LocalDateTime now = LocalDateTime.now();
        this.loginPendingRepository.deleteAllByLoginPendingExpired(now);
    }
}
