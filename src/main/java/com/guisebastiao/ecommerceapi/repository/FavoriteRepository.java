package com.guisebastiao.ecommerceapi.repository;

import com.guisebastiao.ecommerceapi.domain.Favorite;
import com.guisebastiao.ecommerceapi.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {
    @Query("SELECT COUNT(f) > 0 FROM Favorite f WHERE f.client.id = :clientId AND f.product.id = :productId")
    boolean alreadyFavorite(@Param("clientId") UUID clientId, @Param("productId") UUID productId);

    @Query("SELECT f.product FROM Favorite f WHERE f.client.id = :clientId")
    Page<Product> findAllProductsFavoritesByClientId(@Param("clientId") UUID clientId, Pageable pageable);

    @Query("SELECT f FROM Favorite f WHERE f.client.id = :clientId AND f.product.id = :productId")
    Optional<Favorite> findByClientIdAndProductId(@Param("clientId") UUID clientId, @Param("productId") UUID productId);
}
