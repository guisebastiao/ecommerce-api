package com.guisebastiao.ecommerceapi.repository;

import com.guisebastiao.ecommerceapi.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Modifying
    @Query("UPDATE Product p SET p.discount = null WHERE p.discount.endDate < :now")
    void removeDiscountReferenceFromExpiredProducts(@Param("now") LocalDateTime now);
}
