package com.guisebastiao.ecommerceapi.repository;

import com.guisebastiao.ecommerceapi.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT p FROM Product p WHERE (:search IS NULL OR :search = '' OR LOWER(FUNCTION('unaccent', p.name)) LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :search, '%')))) AND (:category IS NULL OR :category = '' OR LOWER(FUNCTION('unaccent', p.category.name)) = LOWER(FUNCTION('unaccent', :category)))")
    Page<Product> findAllBySearchAndCategory(@Param("search") String search, @Param("category") String category, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.product.id = :productId")
    int countCommentsByProduct(@Param("productId") UUID productId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.id = :productId")
    int countReviewsByProduct(@Param("productId") UUID productId);
}
