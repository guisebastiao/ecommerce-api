package com.guisebastiao.ecommerceapi.repository;

import com.guisebastiao.ecommerceapi.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
