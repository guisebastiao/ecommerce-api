package com.guisebastiao.ecommerceapi.domain;

import com.guisebastiao.ecommerceapi.util.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "product_pictures")
public class ProductPicture extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 45, nullable = false, unique = true)
    private String objectId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
