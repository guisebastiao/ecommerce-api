package com.guisebastiao.ecommerceapi.repository;

import com.guisebastiao.ecommerceapi.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    @Query("SELECT ci FROM CartItem ci WHERE ci.product.id = :productId AND ci.cart.client.id = :clientId")
    Optional<CartItem> findCartItemByProductId(@Param("productId") UUID productId, @Param("clientId") UUID clientId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.client.id = :clientId ORDER BY ci.createdAt DESC")
    List<CartItem> findAllByClientId(@Param("clientId") UUID clientId);
}
