package com.guisebastiao.ecommerceapi.mapper;

import com.guisebastiao.ecommerceapi.domain.Review;
import com.guisebastiao.ecommerceapi.dto.request.ReviewRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.ReviewResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface ReviewMapper {
    Review toEntity(ReviewRequestDTO reviewRequestDTO);
    ReviewResponseDTO toDto(Review review);
}
