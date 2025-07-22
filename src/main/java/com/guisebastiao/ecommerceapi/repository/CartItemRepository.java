package com.guisebastiao.ecommerceapi.repository;

import com.guisebastiao.ecommerceapi.domain.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    @Query("SELECT ci FROM CartItem ci WHERE ci.product.id = :productId AND ci.cart.client.id = :clientId")
    Optional<CartItem> findCartItemByProductId(@Param("productId") UUID productId, @Param("clientId") UUID clientId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.client.id = :clientId")
    Page<CartItem> findAllByClientId(@Param("clientId") UUID clientId, Pageable pageable);
}
