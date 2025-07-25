package com.guisebastiao.ecommerceapi.security;

import com.guisebastiao.ecommerceapi.domain.Client;
import com.guisebastiao.ecommerceapi.exception.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ClientAuthProvider {
    public Client getClientAuthenticated() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof Client client){
            return client;
        }

        throw new UnauthorizedException("Autenticação inválida");
    }
}
