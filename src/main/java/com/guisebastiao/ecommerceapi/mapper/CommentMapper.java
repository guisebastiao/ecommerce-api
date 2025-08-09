package com.guisebastiao.ecommerceapi.mapper;

import com.guisebastiao.ecommerceapi.domain.Comment;
import com.guisebastiao.ecommerceapi.dto.request.comment.CommentRequest;
import com.guisebastiao.ecommerceapi.dto.response.comment.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface CommentMapper {
    Comment toEntity(CommentRequest commentRequestDTO);

    @Mapping(source = "id", target = "commentId")
    CommentResponse toDTO(Comment comment);
}
