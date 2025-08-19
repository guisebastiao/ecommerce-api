package com.guisebastiao.ecommerceapi.repository;

import com.guisebastiao.ecommerceapi.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    
}
