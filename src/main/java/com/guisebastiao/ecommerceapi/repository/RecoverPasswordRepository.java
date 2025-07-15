package com.guisebastiao.ecommerceapi.repository;

import com.guisebastiao.ecommerceapi.domain.RecoverPassword;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecoverPasswordRepository extends JpaRepository<RecoverPassword, UUID> {
    @Query("SELECT rp FROM RecoverPassword rp WHERE rp.code = :code")
    Optional<RecoverPassword> findByCode(@Param("code") String code);

    @Modifying
    @Transactional
    @Query("DELETE FROM RecoverPassword rp WHERE rp.expiresCode < :now")
    void deleteAllByRecoverPasswordExpired(@Param("now") LocalDateTime now);

    @Modifying
    @Transactional
    @Query("DELETE FROM RecoverPassword rp WHERE rp.client.id = :userId")
    void deleteByRecoverPasswordByUserId(@Param("userId") UUID userId);
}
