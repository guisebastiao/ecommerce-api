package com.guisebastiao.ecommerceapi.domain;

import com.guisebastiao.ecommerceapi.util.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "client_pictures")
public class ClientPicture extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 45, nullable = false, unique = true)
    private String objectId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
