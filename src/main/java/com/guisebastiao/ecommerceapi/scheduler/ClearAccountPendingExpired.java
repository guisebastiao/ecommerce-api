package com.guisebastiao.ecommerceapi.scheduler;

import com.guisebastiao.ecommerceapi.repository.AccountPendingRepository;
import com.guisebastiao.ecommerceapi.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ClearAccountPendingExpired {

    @Autowired
    private AccountPendingRepository accountPendingRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Scheduled(fixedRate = 60000)
    public void clear() {
        LocalDateTime now = LocalDateTime.now();
        this.accountPendingRepository.deleteAllByAccountPendingExpired(now);
    }
}
