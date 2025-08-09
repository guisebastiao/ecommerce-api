package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.PaginationFilter;
import com.guisebastiao.ecommerceapi.dto.request.favorite.FavoriteRequest;
import com.guisebastiao.ecommerceapi.dto.response.product.ProductResponse;
import com.guisebastiao.ecommerceapi.service.FavoriteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<DefaultResponse<Void>> createFavorite(@RequestBody @Valid FavoriteRequest favoriteRequest) {
        DefaultResponse<Void> response = this.favoriteService.createFavorite(favoriteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<DefaultResponse<PageResponse<ProductResponse>>> findAllFavorites(@Valid PaginationFilter pagination) {
        DefaultResponse<PageResponse<ProductResponse>> response = this.favoriteService.findAllFavorites(pagination.offset(), pagination.limit());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<DefaultResponse<Void>> deleteFavorite(@PathVariable String productId) {
        DefaultResponse<Void> response = this.favoriteService.deleteFavorite(productId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
