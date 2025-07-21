package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.PageResponseDTO;
import com.guisebastiao.ecommerceapi.dto.request.FavoriteRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.ProductResponseDTO;

public interface FavoriteService {
    DefaultDTO<Void> createFavorite(FavoriteRequestDTO favoriteRequestDTO);
    DefaultDTO<PageResponseDTO<ProductResponseDTO>> findAllFavorites(int offset, int limit);
    DefaultDTO<Void> deleteFavorite(String favoriteId);
}
