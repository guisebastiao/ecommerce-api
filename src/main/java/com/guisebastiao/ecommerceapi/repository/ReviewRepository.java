package com.guisebastiao.ecommerceapi.repository;

import com.guisebastiao.ecommerceapi.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    @Query("SELECT r FROM Review r WHERE r.client.id = :clientId AND r.product.id = :productId")
    Review findByClientAndProduct(@Param("clientId") UUID clientId, @Param("productId") UUID productId);
}
