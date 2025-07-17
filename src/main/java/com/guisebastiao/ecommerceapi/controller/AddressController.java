package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.PageResponseDTO;
import com.guisebastiao.ecommerceapi.dto.PaginationFilterDTO;
import com.guisebastiao.ecommerceapi.dto.request.AddressRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.AddressResponseDTO;
import com.guisebastiao.ecommerceapi.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<DefaultDTO<Void>> createAddress(@RequestBody @Valid AddressRequestDTO addressRequestDTO) {
        DefaultDTO<Void> response = this.addressService.createAddress(addressRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<DefaultDTO<PageResponseDTO<AddressResponseDTO>>> findAllAddresses(@Valid PaginationFilterDTO pagination) {
        DefaultDTO<PageResponseDTO<AddressResponseDTO>> response = this.addressService.findAllAddresses(pagination.offset(), pagination.limit());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<DefaultDTO<Void>> updateAddress(@PathVariable String addressId, @RequestBody @Valid AddressRequestDTO addressRequestDTO) {
        DefaultDTO<Void> response = this.addressService.updateAddress(addressId, addressRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<DefaultDTO<Void>> deleteAddress(@PathVariable String addressId) {
        DefaultDTO<Void> response = this.addressService.deleteAddress(addressId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
