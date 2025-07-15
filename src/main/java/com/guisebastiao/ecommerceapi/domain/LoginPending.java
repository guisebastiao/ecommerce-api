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
@Table(name = "logins_pending")
public class LoginPending extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "verification_expires", nullable = false)
    private LocalDateTime verificationExpires;

    @Column(name = "verification_code", length = 6, nullable = false)
    private String verificationCode;

    @Column(length = 45, nullable = false)
    private String code;

    @OneToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
