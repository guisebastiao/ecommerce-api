package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.Client;
import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.request.client.UpdateAccountRequest;
import com.guisebastiao.ecommerceapi.dto.request.client.UpdatePasswordRequest;
import com.guisebastiao.ecommerceapi.dto.response.client.ClientResponse;
import com.guisebastiao.ecommerceapi.dto.response.client.ClientSimpleResponse;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.mapper.ClientMapper;
import com.guisebastiao.ecommerceapi.repository.ClientRepository;
import com.guisebastiao.ecommerceapi.security.AuthProvider;
import com.guisebastiao.ecommerceapi.service.ClientService;
import com.guisebastiao.ecommerceapi.util.UUIDConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private AuthProvider clientAuthProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public DefaultResponse<ClientResponse> findById(String id) {
        Client client = this.clientRepository.findById(UUIDConverter.toUUID(id))
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        ClientResponse clientResponse = this.clientMapper.toDTO(client);

        return new DefaultResponse<ClientResponse>(true, "Cliente encontrado com sucesso", clientResponse);
    }

    @Override
    @Transactional
    public DefaultResponse<ClientSimpleResponse> updateAccount(UpdateAccountRequest updateAccountRequest) {
        Client client = this.clientAuthProvider.getClientAuthenticated();

        client.setName(updateAccountRequest.name());
        client.setSurname(updateAccountRequest.surname());
        client.setPhone(updateAccountRequest.phone());
        client.setBirth(updateAccountRequest.birth());

        Client savedClient = this.clientRepository.save(client);
        ClientSimpleResponse data = this.clientMapper.toSimpleDTO(savedClient);

        return new DefaultResponse<ClientSimpleResponse>(true, "Sua conta foi atualizada com sucesso", data);
    }

    @Override
    @Transactional
    public DefaultResponse<Void> updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        Client client = this.clientAuthProvider.getClientAuthenticated();

        client.setPassword(this.passwordEncoder.encode(updatePasswordRequest.newPassword()));
        this.clientRepository.save(client);

        return new DefaultResponse<Void>(true, "Sua senha foi atualizada com sucesso", null);
    }

    @Override
    @Transactional
    public DefaultResponse<Void> deleteAccount() {
        Client client = this.clientAuthProvider.getClientAuthenticated();
        this.clientRepository.delete(client);
        return new DefaultResponse<Void>(true, "Sua conta foi excluida com sucesso", null);
    }
}
