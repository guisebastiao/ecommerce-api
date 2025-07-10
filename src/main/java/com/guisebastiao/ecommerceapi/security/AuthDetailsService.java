package com.guisebastiao.ecommerceapi.security;

import com.guisebastiao.ecommerceapi.domain.Client;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthDetailsService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws RuntimeException {
        Client client = this.clientRepository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("O cliente não foi encontrado"));

        return new User(client.getEmail(), client.getPassword(), List.of());
    }
}
