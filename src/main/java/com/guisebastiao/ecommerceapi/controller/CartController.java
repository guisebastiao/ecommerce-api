package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.PageResponseDTO;
import com.guisebastiao.ecommerceapi.dto.PaginationFilterDTO;
import com.guisebastiao.ecommerceapi.dto.request.CartItemRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.CartItemResponseDTO;
import com.guisebastiao.ecommerceapi.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<DefaultDTO<Void>> addProductToCart(@RequestBody @Valid CartItemRequestDTO cartItemRequestDTO) {
        DefaultDTO<Void> response = this.cartService.addProductToCart(cartItemRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<DefaultDTO<PageResponseDTO<CartItemResponseDTO>>> findAllCartItems(@Valid PaginationFilterDTO pagination) {
        DefaultDTO<PageResponseDTO<CartItemResponseDTO>> response = this.cartService.findAllCartItems(pagination.offset(), pagination.limit());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<DefaultDTO<Void>> removeProductFromCart(@PathVariable String cartItemId) {
        DefaultDTO<Void> response = this.cartService.removeProductFromCart(cartItemId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{cartItemId}/remove-all")
    public ResponseEntity<DefaultDTO<Void>> removeAllProductsFromCart(@PathVariable String cartItemId) {
        DefaultDTO<Void> response = this.cartService.removeAllProductsFromCart(cartItemId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
