package com.guisebastiao.ecommerceapi.repository;

import com.guisebastiao.ecommerceapi.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    @Query("SELECT c FROM Comment c WHERE c.product.id = :productId")
    Page<Comment> findAllByCommentId(@Param("productId") UUID productId, Pageable pageable);
}
