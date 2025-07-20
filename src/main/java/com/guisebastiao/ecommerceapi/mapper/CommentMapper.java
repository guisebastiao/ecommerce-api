package com.guisebastiao.ecommerceapi.mapper;

import com.guisebastiao.ecommerceapi.domain.Comment;
import com.guisebastiao.ecommerceapi.dto.request.CommentRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.CommentResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface CommentMapper {
    Comment toEntity(CommentRequestDTO commentRequestDTO);
    CommentResponseDTO toDto(Comment comment);
}
