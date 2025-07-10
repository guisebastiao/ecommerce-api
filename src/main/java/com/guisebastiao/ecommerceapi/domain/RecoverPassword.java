package com.guisebastiao.ecommerceapi.domain;

import com.guisebastiao.ecommerceapi.util.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "recover_passwords")
public class RecoverPassword extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 45, unique = true)
    private String code;

    @Column(name = "expires_code")
    private LocalDateTime expiresCode;

    @OneToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
