package com.guisebastiao.ecommerceapi.repository;

import com.guisebastiao.ecommerceapi.domain.Discount;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

public interface DiscountRepository extends JpaRepository<Discount, UUID> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Discount d WHERE d.endDate < :now")
    void deleteAllByDiscountExpired(@Param("now") LocalDateTime now);
}
