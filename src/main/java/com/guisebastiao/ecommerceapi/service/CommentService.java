package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.PageResponseDTO;
import com.guisebastiao.ecommerceapi.dto.request.CommentRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.CommentResponseDTO;

public interface CommentService {
    DefaultDTO<Void> createComment(String productId, CommentRequestDTO commentRequestDTO);
    DefaultDTO<PageResponseDTO<CommentResponseDTO>> findAllComments(String productId, int offset, int limit);
    DefaultDTO<Void> updateComment(String commentId, CommentRequestDTO commentRequestDTO);
    DefaultDTO<Void> deleteComment(String commentId);
}
