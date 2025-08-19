package com.guisebastiao.ecommerceapi.repository;

import com.guisebastiao.ecommerceapi.domain.Discount;
import com.guisebastiao.ecommerceapi.domain.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

public interface DiscountRepository extends JpaRepository<Discount, UUID> {
    @Modifying
    @Query("DELETE FROM Discount d WHERE d.endDate < :now")
    void deleteAllByDiscountExpired(@Param("now") LocalDateTime now);

    @Query("SELECT CASE WHEN (COUNT(d) > 0) THEN true ELSE false END FROM Discount d WHERE d.id = :discountId")
    boolean existsByDiscountId(@Param("discountId") UUID discountId);

    @Query("SELECT p FROM Product p WHERE p.discount.id = :discountId")
    Page<Product> findAllProductByDiscountId(@Param("discountId") UUID discountId, Pageable pageable);
}
