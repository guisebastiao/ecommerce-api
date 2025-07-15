package com.guisebastiao.ecommerceapi.repository;

import com.guisebastiao.ecommerceapi.domain.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    @Query("SELECT a FROM Address a WHERE a.client.id = :clientId")
    Page<Address> findAllByClientId(@Param("clientId") UUID clientId, Pageable pageable);
}
