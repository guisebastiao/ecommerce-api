package com.guisebastiao.ecommerceapi.service;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.request.comment.CommentRequest;
import com.guisebastiao.ecommerceapi.dto.response.comment.CommentResponse;

public interface CommentService {
    DefaultResponse<Void> createComment(String productId, CommentRequest commentRequest);
    DefaultResponse<PageResponse<CommentResponse>> findAllComments(String productId, int offset, int limit);
    DefaultResponse<Void> updateComment(String commentId, CommentRequest commentRequest);
    DefaultResponse<Void> deleteComment(String commentId);
}
