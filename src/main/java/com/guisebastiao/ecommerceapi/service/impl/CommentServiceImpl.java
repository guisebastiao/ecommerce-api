package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.Client;
import com.guisebastiao.ecommerceapi.domain.Comment;
import com.guisebastiao.ecommerceapi.domain.Product;
import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.Paging;
import com.guisebastiao.ecommerceapi.dto.request.comment.CommentRequest;
import com.guisebastiao.ecommerceapi.dto.response.comment.CommentResponse;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.exception.UnauthorizedException;
import com.guisebastiao.ecommerceapi.mapper.CommentMapper;
import com.guisebastiao.ecommerceapi.repository.CommentRepository;
import com.guisebastiao.ecommerceapi.repository.ProductRepository;
import com.guisebastiao.ecommerceapi.security.AuthProvider;
import com.guisebastiao.ecommerceapi.service.CommentService;
import com.guisebastiao.ecommerceapi.util.UUIDConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuthProvider clientAuthProvider;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    @Transactional
    public DefaultResponse<Void> createComment(String productId, CommentRequest commentRequest) {
        Client client = this.clientAuthProvider.getClientAuthenticated();
        Product product = this.findProduct(productId);

        Comment comment = this.commentMapper.toEntity(commentRequest);
        comment.setProduct(product);
        comment.setClient(client);

        this.commentRepository.save(comment);

        return new DefaultResponse<Void>(true, "Comentário criado com sucesso", null);
    }

    @Override
    public DefaultResponse<PageResponse<CommentResponse>> findAllComments(String productId, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset - 1, limit, Sort.by("createdAt").descending());

        Page<Comment> resultPage = this.commentRepository.findAllByCommentId(UUIDConverter.toUUID(productId), pageable);

        Paging paging = new Paging(resultPage.getTotalElements(), resultPage.getTotalPages(), offset, limit);

        List<CommentResponse> dataResponse = resultPage.getContent().stream().map(this.commentMapper::toDTO).toList();

        PageResponse<CommentResponse> data = new PageResponse<CommentResponse>(dataResponse, paging);

        return new DefaultResponse<PageResponse<CommentResponse>>(true, "Comentários retornados com sucesso", data);
    }

    @Override
    @Transactional
    public DefaultResponse<Void> updateComment(String commentId, CommentRequest commentRequest) {
        Client client = this.clientAuthProvider.getClientAuthenticated();
        Comment comment = this.findComment(commentId);

        if(!client.getId().equals(comment.getClient().getId())) {
            throw new UnauthorizedException("Você não tem permissão para editar esse comentário");
        }

        comment.setContent(commentRequest.content());

        this.commentRepository.save(comment);

        return new DefaultResponse<Void>(true, "Comentário atualizado com sucesso", null);
    }

    @Override
    @Transactional
    public DefaultResponse<Void> deleteComment(String commentId) {
        Client client = this.clientAuthProvider.getClientAuthenticated();
        Comment comment = this.findComment(commentId);

        if(!client.getId().equals(comment.getClient().getId())) {
            throw new UnauthorizedException("Você não tem permissão para editar esse comentário");
        }

        this.commentRepository.delete(comment);

        return new DefaultResponse<Void>(true, "Comentário excluido com sucesso", null);
    }

    private Comment findComment(String commentId) {
        return this.commentRepository.findById(UUIDConverter.toUUID(commentId)).
                orElseThrow(() -> new EntityNotFoundException("Comentário não encontrado"));
    }

    private Product findProduct(String productId) {
        return this.productRepository.findById(UUIDConverter.toUUID(productId))
            .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
    }
}
