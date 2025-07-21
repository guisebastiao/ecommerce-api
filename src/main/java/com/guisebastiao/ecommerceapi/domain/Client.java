package com.guisebastiao.ecommerceapi.domain;

import com.guisebastiao.ecommerceapi.enums.AccountStatus;
import com.guisebastiao.ecommerceapi.enums.Role;
import com.guisebastiao.ecommerceapi.util.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client extends Auditable implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String surname;

    @Column(nullable = false, length = 11)
    private String cpf;

    @Column(nullable = false, length = 13)
    private String phone;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false, length = 250, unique = true)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(length = 500)
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private AccountPending accountPending;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private LoginPending loginPending;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private RecoverPassword recoverPassword;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private ClientPicture clientPicture;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Address> address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorites;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status != AccountStatus.BANNED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status == AccountStatus.ACTIVE;
    }
}
