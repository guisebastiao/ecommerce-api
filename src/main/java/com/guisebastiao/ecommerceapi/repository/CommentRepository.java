package com.guisebastiao.ecommerceapi.repository;

import com.guisebastiao.ecommerceapi.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}
