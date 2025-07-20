package com.guisebastiao.ecommerceapi.controller;

import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.PageResponseDTO;
import com.guisebastiao.ecommerceapi.dto.PaginationFilterDTO;
import com.guisebastiao.ecommerceapi.dto.request.CommentRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.CommentResponseDTO;
import com.guisebastiao.ecommerceapi.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/{productId}")
    public ResponseEntity<DefaultDTO<Void>> createComment(@PathVariable String productId, @RequestBody @Valid CommentRequestDTO commentRequestDTO) {
        DefaultDTO<Void> response = this.commentService.createComment(productId, commentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<DefaultDTO<PageResponseDTO<CommentResponseDTO>>> findAllComments(@PathVariable String productId, @Valid PaginationFilterDTO pagination) {
        DefaultDTO<PageResponseDTO<CommentResponseDTO>> response = this.commentService.findAllComments(productId, pagination.offset(), pagination.limit());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<DefaultDTO<Void>> updateComment(@PathVariable String commentId, @RequestBody @Valid CommentRequestDTO commentRequestDTO) {
        DefaultDTO<Void> response = this.commentService.updateComment(commentId, commentRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<DefaultDTO<Void>> deleteComment(@PathVariable String commentId) {
        DefaultDTO<Void> response = this.commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
