package com.guisebastiao.ecommerceapi.domain;

import com.guisebastiao.ecommerceapi.util.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "addresses_clients")
public class Address extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 250)
    private String street;

    @Column(nullable = false, length = 20)
    private String number;

    @Column(length = 100)
    private String complement;

    @Column(nullable = false, length = 100)
    private String neighborhood;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 100)
    private String state;

    @Column(nullable = false, length = 9)
    private String zip;

    @Column(nullable = false, length = 100)
    private String country;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
