package com.guisebastiao.ecommerceapi.repository;

import com.guisebastiao.ecommerceapi.domain.ProductPicture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductPictureRepository extends JpaRepository<ProductPicture, UUID> {
}
