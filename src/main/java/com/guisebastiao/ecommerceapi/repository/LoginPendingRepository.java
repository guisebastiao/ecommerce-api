package com.guisebastiao.ecommerceapi.repository;

import com.guisebastiao.ecommerceapi.domain.LoginPending;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface LoginPendingRepository extends JpaRepository<LoginPending, UUID> {
    @Query("SELECT lp FROM LoginPending lp WHERE lp.verificationCode = :verificationCode")
    Optional<LoginPending> findByVerificationCode(@Param("verificationCode") String verificationCode);

    @Modifying
    @Transactional
    @Query("DELETE FROM LoginPending lp WHERE lp.client.id = :clientId")
    void deleteByClientId(@Param("clientId") UUID clientId);

    @Modifying
    @Transactional
    @Query("DELETE FROM LoginPending lp WHERE lp.verificationExpires < :now")
    void deleteAllByLoginPendingExpired(@Param("now") LocalDateTime now);
}
