package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.PaginationFilter;
import com.guisebastiao.ecommerceapi.dto.request.address.AddressRequest;
import com.guisebastiao.ecommerceapi.dto.response.address.AddressResponse;
import com.guisebastiao.ecommerceapi.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<DefaultResponse<Void>> createAddress(@RequestBody @Valid AddressRequest addressRequest) {
        DefaultResponse<Void> response = this.addressService.createAddress(addressRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<DefaultResponse<PageResponse<AddressResponse>>> findAllAddresses(@Valid PaginationFilter pagination) {
        DefaultResponse<PageResponse<AddressResponse>> response = this.addressService.findAllAddresses(pagination.offset(), pagination.limit());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<DefaultResponse<Void>> updateAddress(@PathVariable String addressId, @RequestBody @Valid AddressRequest addressRequest) {
        DefaultResponse<Void> response = this.addressService.updateAddress(addressId, addressRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<DefaultResponse<Void>> deleteAddress(@PathVariable String addressId) {
        DefaultResponse<Void> response = this.addressService.deleteAddress(addressId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
