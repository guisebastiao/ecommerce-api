package com.guisebastiao.ecommerceapi.repository;

import com.guisebastiao.ecommerceapi.domain.ClientPicture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientPictureRepository extends JpaRepository<ClientPicture, UUID> {
}
