package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.request.favorite.FavoriteRequest;
import com.guisebastiao.ecommerceapi.dto.response.product.ProductResponse;

public interface FavoriteService {
    DefaultResponse<Void> createFavorite(FavoriteRequest favoriteRequest);
    DefaultResponse<PageResponse<ProductResponse>> findAllFavorites(int offset, int limit);
    DefaultResponse<Void> deleteFavorite(String favoriteId);
}
