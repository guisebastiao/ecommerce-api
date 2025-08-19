package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.PaginationFilter;
import com.guisebastiao.ecommerceapi.dto.request.comment.CommentRequest;
import com.guisebastiao.ecommerceapi.dto.response.comment.CommentResponse;
import com.guisebastiao.ecommerceapi.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/{productId}")
    public ResponseEntity<DefaultResponse<Void>> createComment(@PathVariable String productId, @RequestBody @Valid CommentRequest commentRequest) {
        DefaultResponse<Void> response = this.commentService.createComment(productId, commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<DefaultResponse<PageResponse<CommentResponse>>> findAllComments(@PathVariable String productId, @Valid PaginationFilter pagination) {
        DefaultResponse<PageResponse<CommentResponse>> response = this.commentService.findAllComments(productId, pagination.offset(), pagination.limit());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<DefaultResponse<Void>> updateComment(@PathVariable String commentId, @RequestBody @Valid CommentRequest commentRequest) {
        DefaultResponse<Void> response = this.commentService.updateComment(commentId, commentRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<DefaultResponse<Void>> deleteComment(@PathVariable String commentId) {
        DefaultResponse<Void> response = this.commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
