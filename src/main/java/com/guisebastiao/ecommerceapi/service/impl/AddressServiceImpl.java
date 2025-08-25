package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.Address;
import com.guisebastiao.ecommerceapi.domain.Client;
import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.Paging;
import com.guisebastiao.ecommerceapi.dto.request.address.AddressRequest;
import com.guisebastiao.ecommerceapi.dto.response.address.AddressResponse;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.exception.UnauthorizedException;
import com.guisebastiao.ecommerceapi.mapper.AddressMapper;
import com.guisebastiao.ecommerceapi.repository.AddressRepository;
import com.guisebastiao.ecommerceapi.security.AuthProvider;
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
    private AuthProvider authProvider;

    @Autowired
    private AddressMapper addressMapper;

    @Override
    @Transactional
    public DefaultResponse<Void> createAddress(AddressRequest addressRequest) {
        Client client = this.authProvider.getClientAuthenticated();

        Address address = this.addressMapper.toEntity(addressRequest);
        address.setClient(client);

        this.addressRepository.save(address);

        return new DefaultResponse<Void>(true, "Seu endereço foi criado com sucesso", null);
    }

    @Override
    public DefaultResponse<PageResponse<AddressResponse>> findAllAddresses(int offset, int limit) {
        Client client = this.authProvider.getClientAuthenticated();

        Pageable pageable = PageRequest.of(offset - 1, limit);

        Page<Address> resultPage = this.addressRepository.findAllByClientId(client.getId(), pageable);

        Paging paging = new Paging(resultPage.getTotalElements(), resultPage.getTotalPages(), offset, limit);

        List<AddressResponse> dataResponse = resultPage.getContent().stream().map(this.addressMapper::toDto).toList();

        PageResponse<AddressResponse> data = new PageResponse<AddressResponse>(dataResponse, paging);

        return new DefaultResponse<PageResponse<AddressResponse>>(true, "Endereços retornados com sucesso", data);
    }

    @Override
    @Transactional
    public DefaultResponse<Void> updateAddress(String addressId, AddressRequest addressRequest) {
        Client client = this.authProvider.getClientAuthenticated();

        Address address = this.findAddressById(addressId);

        if(!address.getClient().getId().equals(client.getId())) {
            throw new UnauthorizedException("Você não tem permissão para editar esse endereço");
        }

        address.setStreet(addressRequest.street());
        address.setNumber(addressRequest.number());
        address.setComplement(addressRequest.complement());
        address.setNeighborhood(addressRequest.neighborhood());
        address.setCity(addressRequest.city());
        address.setState(addressRequest.state());
        address.setZip(addressRequest.zip());
        address.setCountry(addressRequest.country());

        this.addressRepository.save(address);

        return new DefaultResponse<Void>(true, "Seu endereço foi atualizado com sucesso", null);
    }

    @Override
    @Transactional
    public DefaultResponse<Void> deleteAddress(String addressId) {
        Client client = this.authProvider.getClientAuthenticated();

        Address address = this.findAddressById(addressId);

        if(!address.getClient().getId().equals(client.getId())) {
            throw new UnauthorizedException("Você não tem permissão para excluir esse endereço");
        }

        this.addressRepository.delete(address);

        return new DefaultResponse<Void>(true, "Seu endereço foi excluido com sucesso", null);
    }

    private Address findAddressById(String addressId) {
        return this.addressRepository.findById(UUIDConverter.toUUID(addressId))
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));
    }
}
