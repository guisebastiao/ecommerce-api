package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.Address;
import com.guisebastiao.ecommerceapi.domain.Client;
import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.PageResponseDTO;
import com.guisebastiao.ecommerceapi.dto.PagingDTO;
import com.guisebastiao.ecommerceapi.dto.request.AddressRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.AddressResponseDTO;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.exception.UnauthorizedException;
import com.guisebastiao.ecommerceapi.mapper.AddressMapper;
import com.guisebastiao.ecommerceapi.repository.AddressRepository;
import com.guisebastiao.ecommerceapi.security.ClientAuthProvider;
import com.guisebastiao.ecommerceapi.service.AddressService;
import com.guisebastiao.ecommerceapi.util.UUIDConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ClientAuthProvider clientAuthProvider;

    @Autowired
    private AddressMapper addressMapper;

    @Override
    @Transactional
    public DefaultDTO<Void> createAddress(AddressRequestDTO addressRequestDTO) {
        Client client = this.clientAuthProvider.getClientAuthenticated();

        Address address = this.addressMapper.toEntity(addressRequestDTO);
        address.setClient(client);

        this.addressRepository.save(address);

        return new DefaultDTO<Void>(Boolean.TRUE, "Seu endereço foi criado com sucesso", null);
    }

    @Override
    public DefaultDTO<PageResponseDTO<AddressResponseDTO>> findAllAddresses(int offset, int limit) {
        Client client = this.clientAuthProvider.getClientAuthenticated();

        Pageable pageable = PageRequest.of(offset, limit);

        Page<Address> resultPage = this.addressRepository.findAllByClientId(client.getId(), pageable);

        PagingDTO pagingDTO = new PagingDTO(resultPage.getTotalElements(), resultPage.getTotalPages(), offset, limit);

        List<AddressResponseDTO> dataResponse = resultPage.getContent().stream().map(this.addressMapper::toDto).toList();

        PageResponseDTO<AddressResponseDTO> data = new PageResponseDTO<AddressResponseDTO>(dataResponse, pagingDTO);

        return new DefaultDTO<PageResponseDTO<AddressResponseDTO>>(Boolean.TRUE, "Endereços retornados com sucesso", data);
    }

    @Override
    @Transactional
    public DefaultDTO<Void> updateAddress(String addressId, AddressRequestDTO addressRequestDTO) {
        Client client = this.clientAuthProvider.getClientAuthenticated();

        Address address = this.findAddressById(addressId);

        if(!address.getClient().getId().equals(client.getId())) {
            throw new UnauthorizedException("Você não tem permissão para editar esse endereço");
        }

        address.setStreet(addressRequestDTO.street());
        address.setNumber(addressRequestDTO.number());
        address.setComplement(addressRequestDTO.complement());
        address.setNeighborhood(addressRequestDTO.neighborhood());
        address.setCity(addressRequestDTO.city());
        address.setState(addressRequestDTO.state());
        address.setZip(addressRequestDTO.zip());
        address.setCountry(addressRequestDTO.country());

        this.addressRepository.save(address);

        return new DefaultDTO<Void>(Boolean.TRUE, "Seu endereço foi atualizado com sucesso", null);
    }

    @Override
    @Transactional
    public DefaultDTO<Void> deleteAddress(String addressId) {
        Client client = this.clientAuthProvider.getClientAuthenticated();

        Address address = this.findAddressById(addressId);

        if(!address.getClient().getId().equals(client.getId())) {
            throw new UnauthorizedException("Você não tem permissão para excluir esse endereço");
        }

        this.addressRepository.delete(address);

        return new DefaultDTO<Void>(Boolean.TRUE, "Seu endereço foi excluido com sucesso", null);
    }

    private Address findAddressById(String addressId) {
        return this.addressRepository.findById(UUIDConverter.toUUID(addressId))
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));
    }
}
