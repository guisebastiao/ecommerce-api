package com.guisebastiao.ecommerceapi.scheduler;

import com.guisebastiao.ecommerceapi.repository.AccountPendingRepository;
import com.guisebastiao.ecommerceapi.repository.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class ClearAccountPendingExpired {

    @Autowired
    private AccountPendingRepository accountPendingRepository;

    @Scheduled(fixedRate = 60000)
    public void clear() {
        LocalDateTime now = LocalDateTime.now();
        this.accountPendingRepository.deleteAllByAccountPendingExpired(now);
    }
}
