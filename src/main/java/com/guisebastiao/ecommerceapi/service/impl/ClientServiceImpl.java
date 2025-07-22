package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.Client;
import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.request.UpdateAccountRequestDTO;
import com.guisebastiao.ecommerceapi.dto.request.UpdatePasswordRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.ClientResponseDTO;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.mapper.ClientMapper;
import com.guisebastiao.ecommerceapi.repository.ClientRepository;
import com.guisebastiao.ecommerceapi.security.ClientAuthProvider;
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
    private ClientAuthProvider clientAuthProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public DefaultDTO<ClientResponseDTO> findById(String id) {
        Client client = this.clientRepository.findById(UUIDConverter.toUUID(id))
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        ClientResponseDTO clientResponseDTO = this.clientMapper.toDto(client);

        return new DefaultDTO<ClientResponseDTO>(Boolean.TRUE, "Cliente encontrado com sucesso", clientResponseDTO);
    }

    @Override
    @Transactional
    public DefaultDTO<Void> updateAccount(UpdateAccountRequestDTO updateAccountRequestDTO) {
        Client client = this.clientAuthProvider.getClientAuthenticated();

        client.setName(updateAccountRequestDTO.name());
        client.setSurname(updateAccountRequestDTO.surname());
        client.setPhone(updateAccountRequestDTO.phone());
        client.setBirth(updateAccountRequestDTO.birth());

        this.clientRepository.save(client);

        return new DefaultDTO<Void>(Boolean.TRUE, "Sua conta foi atualizada com sucesso", null);
    }

    @Override
    @Transactional
    public DefaultDTO<Void> updatePassword(UpdatePasswordRequestDTO updatePasswordRequestDTO) {
        Client client = this.clientAuthProvider.getClientAuthenticated();

        client.setPassword(this.passwordEncoder.encode(updatePasswordRequestDTO.newPassword()));
        this.clientRepository.save(client);

        return new DefaultDTO<Void>(Boolean.TRUE, "Sua senha foi atualizada com sucesso", null);
    }

    @Override
    @Transactional
    public DefaultDTO<Void> deleteAccount() {
        Client client = this.clientAuthProvider.getClientAuthenticated();
        this.clientRepository.delete(client);
        return new DefaultDTO<Void>(Boolean.TRUE, "Sua conta foi excluida com sucesso", null);
    }
}
