package com.guisebastiao.ecommerceapi.repository;

import com.guisebastiao.ecommerceapi.domain.AccountPending;
import com.guisebastiao.ecommerceapi.domain.LoginPending;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountPendingRepository extends JpaRepository<AccountPending, UUID> {
    @Query("SELECT ap FROM AccountPending ap WHERE ap.verificationCode = :verificationCode")
    Optional<AccountPending> findByVerificationCode(@Param("verificationCode") String verificationCode);

    @Modifying
    @Transactional
    @Query("DELETE FROM AccountPending ap WHERE ap.client.id = :clientId")
    void deleteAccountPendingByClientId(@Param("clientId") UUID clientId);

    @Modifying
    @Transactional
    @Query("DELETE FROM AccountPending ap WHERE ap.verificationExpires < :now")
    void deleteAllByAccountPendingExpired(@Param("now") LocalDateTime now);
}
