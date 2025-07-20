package com.guisebastiao.ecommerceapi.scheduler;

import com.guisebastiao.ecommerceapi.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ClearDiscountExpired {

    @Autowired
    private DiscountRepository discountRepository;

    @Scheduled(fixedRate = 60000)
    public void clear() {
        LocalDateTime now = LocalDateTime.now();
        this.discountRepository.deleteAllByDiscountExpired(now);
    }
}
