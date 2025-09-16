package com.guisebastiao.ecommerceapi.mapper;

import com.guisebastiao.ecommerceapi.domain.Comment;
import com.guisebastiao.ecommerceapi.dto.request.comment.CommentRequest;
import com.guisebastiao.ecommerceapi.dto.response.comment.CommentResponse;
import com.guisebastiao.ecommerceapi.mapper.resolver.CommentResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ClientMapper.class, CommentResolver.class})
public interface CommentMapper {
    Comment toEntity(CommentRequest commentRequestDTO);

    @Mapping(source = "id", target = "commentId")
    @Mapping(target = "belongsToAuthUser", source = ".", qualifiedByName = "resolveBelongsToAuthUser")
    CommentResponse toDTO(Comment comment);
}
