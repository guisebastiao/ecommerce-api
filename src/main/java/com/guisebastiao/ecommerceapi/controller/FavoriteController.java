package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.PageResponseDTO;
import com.guisebastiao.ecommerceapi.dto.PaginationFilterDTO;
import com.guisebastiao.ecommerceapi.dto.request.FavoriteRequestDTO;
import com.guisebastiao.ecommerceapi.dto.request.ProductRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.ProductResponseDTO;
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
    public ResponseEntity<DefaultDTO<Void>> createFavorite(@RequestBody @Valid FavoriteRequestDTO favoriteRequestDTO) {
        DefaultDTO<Void> response = this.favoriteService.createFavorite(favoriteRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<DefaultDTO<PageResponseDTO<ProductResponseDTO>>> findAllFavorites(@Valid PaginationFilterDTO pagination) {
        DefaultDTO<PageResponseDTO<ProductResponseDTO>> response = this.favoriteService.findAllFavorites(pagination.offset(), pagination.limit());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<DefaultDTO<Void>> deleteFavorite(@PathVariable String favoriteId) {
        DefaultDTO<Void> response = this.favoriteService.deleteFavorite(favoriteId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
