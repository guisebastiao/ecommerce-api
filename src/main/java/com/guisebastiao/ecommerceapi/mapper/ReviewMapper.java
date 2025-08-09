package com.guisebastiao.ecommerceapi.mapper;

import com.guisebastiao.ecommerceapi.domain.Review;
import com.guisebastiao.ecommerceapi.dto.request.review.ReviewRequest;
import com.guisebastiao.ecommerceapi.dto.response.review.ReviewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface ReviewMapper {
    Review toEntity(ReviewRequest reviewRequestDTO);

    @Mapping(source = "id", target = "reviewId")
    ReviewResponse toDTO(Review review);
}
