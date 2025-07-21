package com.guisebastiao.ecommerceapi.mapper;

import com.guisebastiao.ecommerceapi.domain.Favorite;
import com.guisebastiao.ecommerceapi.dto.request.FavoriteRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface FavoriteMapper {
    Favorite toEntity(FavoriteRequestDTO favoriteRequestDTO);
}
